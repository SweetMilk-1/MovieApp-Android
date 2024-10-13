package ru.sweetmilk.movieapp.cases.movieList

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding

class MovieListAdapter(
    context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val loadMovieImageFunc: suspend (id: String) -> Bitmap?
) : ListAdapter<MovieListItem, MovieListItemViewHolder>(
    MovieListItemDiffUtil()
) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListItemViewHolder {
        val binding = HolderMovieListItemBinding.inflate(layoutInflater, parent, false)
        return MovieListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        lifecycleScope.launch {
            val bitmap = loadMovieImageFunc.invoke(item.id)
            holder.bindImage(bitmap)
        }
    }
}