package ru.sweetmilk.movieapp.cases.movieList

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.movie.MovieListItem
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemLoadingBinding
import java.security.InvalidParameterException
import java.util.UUID

class MovieListAdapter(
    context: Context,
    private val coroutineScope: CoroutineScope,
    private val fetchMovieImageCallback: suspend (id: UUID) -> Drawable?,
    private val fetchNewDataCallback: () -> Unit
) : RecyclerView.Adapter<MovieListItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var isLastPage = true
    private var isLoading = false
    private var movieItems = listOf<MovieListItem>()

    fun onLoadingFailed() {
        submitItems(movieItems, true)
    }

    fun submitItems(newMovieItems: List<MovieListItem>, isLastPage: Boolean) {
        this.isLastPage = isLastPage
        isLoading = false

        notifyItemRemoved(movieItems.size)

        val diffCallback = MovieListDiffUtilCallback(movieItems, newMovieItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        movieItems = newMovieItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movieItems.size)
            LOADING_VIEW_TYPE
        else
            MOVIE_VIEW_TYPE
    }

    override fun getItemCount(): Int = movieItems.size + if (isLastPage) 0 else 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListItemViewHolder {
        return when (viewType) {
            MOVIE_VIEW_TYPE -> {
                val binding = HolderMovieListItemBinding.inflate(layoutInflater, parent, false)
                MovieViewHolder(binding)
            }

            LOADING_VIEW_TYPE -> {
                val binding =
                    HolderMovieListItemLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }

            else -> throw InvalidParameterException("viewType=$viewType is invalid")
        }
    }

    override fun onBindViewHolder(holder: MovieListItemViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            val item = movieItems[position]
            holder.bind(item)
            coroutineScope.launch {
                val drawable = fetchMovieImageCallback.invoke(item.id)
                holder.bindImage(drawable)
            }

            if (position == movieItems.size - 1 && !isLastPage && !isLoading) {
                fetchNewDataCallback.invoke()
                isLoading = true
            }
        }
    }

    companion object {
        private val LOADING_VIEW_TYPE = 1
        private val MOVIE_VIEW_TYPE = 2
    }
}