package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.cases.movieList.viewModel.MovieListItemViewModel
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding

class MovieListItemViewHolder(private val binding: HolderMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = MovieListItemViewModel()
    }

    fun bind(movieListItem: MovieListItem) {
        binding.viewModel?.movieListItem = movieListItem
    }

    fun bindImage(bitmap: Bitmap?) {
        binding.movieImage.setImageBitmap(bitmap)
    }
}