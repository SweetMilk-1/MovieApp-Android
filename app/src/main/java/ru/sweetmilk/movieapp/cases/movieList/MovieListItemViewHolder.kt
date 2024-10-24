package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.movie.MovieListItem
import ru.sweetmilk.movieapp.cases.movieDetails.MovieDetailsFragment
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemLoadingBinding
import java.util.Calendar

abstract class MovieListItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

class MovieViewHolder(private val binding: HolderMovieListItemBinding) :
    MovieListItemViewHolder(binding.root), View.OnClickListener {

    private var movieListItem: MovieListItem? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(movieListItem: MovieListItem) {
        this.movieListItem = movieListItem

        binding.movieImage.setImageDrawable(null)

        binding.movieTitle.text = movieListItem.title

        binding.moviePgInfo.apply {
            isVisible = !movieListItem.pgInfo.isNullOrEmpty()
            text = movieListItem.pgInfo
        }

        binding.movieYear.apply {
            val date = getYearFromDate()
            isVisible = !date.isNullOrEmpty()
            text = date
        }

        binding.movieGenres.apply {
            val genres = movieListItem.genres.joinToString(transform = { it.name }).lowercase()
            isVisible = genres.isNotEmpty()
            text = genres
        }
    }

    private fun getYearFromDate(): String? {
        if (movieListItem?.releaseDate == null) {
            return null
        }
        val cal: Calendar = Calendar.getInstance().apply {
            time = movieListItem?.releaseDate!!
        }
        return cal.get(Calendar.YEAR).toString()
    }

    fun bindImage(image: Drawable?) {
        val drawable =
            image ?: ResourcesCompat.getDrawable(itemView.resources, R.drawable.no_photo, null)
        binding.movieImage.setImageDrawable(drawable)
    }

    override fun onClick(v: View?) {
        val bundle = Bundle().apply {
            putSerializable(MovieDetailsFragment.ARG_MOVIE_ID, movieListItem?.id)
        }
        itemView.findNavController()
            .navigate(R.id.action_movie_list_to_details, bundle)
    }
}

class LoadingViewHolder(binding: HolderMovieListItemLoadingBinding) :
    MovieListItemViewHolder(binding.root) {

}