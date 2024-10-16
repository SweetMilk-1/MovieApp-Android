package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.cases.movieDetails.MovieDetailsFragment
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding
import ru.sweetmilk.movieapp.utils.DictionaryToStringConverter
import java.util.Calendar


class MovieListItemViewHolder(private val binding: HolderMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var movieListItem: MovieListItem? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(movieListItem: MovieListItem) {
        this.movieListItem = movieListItem
        binding.movieId.text = movieListItem.id.toString()
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
            val genres = DictionaryToStringConverter.convert(movieListItem.genres)
            isVisible = !genres.isNullOrEmpty()
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

    fun bindImage(bitmap: Bitmap?) {
        binding.movieImage.setImageBitmap(bitmap)
    }

    override fun onClick(v: View?) {
        val bundle = Bundle().apply {
            putSerializable(MovieDetailsFragment.ARG_MOVIE_ID, movieListItem?.id)
        }
        itemView.findNavController()
            .navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }
}