package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.cases.movieDetails.MovieDetailsFragment
import ru.sweetmilk.movieapp.databinding.HolderMovieListItemBinding

class MovieListItemViewHolder(private val binding: HolderMovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var movieListItem : MovieListItem? = null

    init {
        itemView.setOnClickListener(this)
        binding.movieListItemViewModel = MovieListItemViewModel()
    }

    fun bind(movieListItem: MovieListItem) {
        this.movieListItem = movieListItem
        binding.movieListItemViewModel?.movieListItem = movieListItem
    }

    fun bindImage(bitmap: Bitmap?) {
        binding.movieImage.setImageBitmap(bitmap)
    }

    override fun onClick(v: View?) {
        val bundle = Bundle().apply {
            putSerializable(MovieDetailsFragment.ARG_MOVIE_ID, movieListItem?.id)
        }
        itemView.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }
}