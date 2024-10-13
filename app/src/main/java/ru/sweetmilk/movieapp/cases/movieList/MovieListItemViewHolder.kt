package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding

class MovieListItemViewHolder(private val binding: HolderMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.movieListItemViewModel = MovieListItemViewModel()
    }

    fun bind(movieListItem: MovieListItem) {
        binding.movieListItemViewModel?.movieListItem = movieListItem
    }

    fun bindImage(bitmap: Bitmap?) {
        binding.movieImage.setImageBitmap(bitmap)
    }
}