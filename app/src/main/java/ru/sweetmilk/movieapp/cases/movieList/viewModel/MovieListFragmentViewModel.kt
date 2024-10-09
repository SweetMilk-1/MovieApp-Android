package ru.sweetmilk.movieapp.cases.movieList.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository

class MovieListFragmentViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    fun getMovieListLiveData() = movieRepository.getMoviesList()

    suspend fun getMovieImage(id: String): Bitmap = movieRepository.getMovieImage(id)
}