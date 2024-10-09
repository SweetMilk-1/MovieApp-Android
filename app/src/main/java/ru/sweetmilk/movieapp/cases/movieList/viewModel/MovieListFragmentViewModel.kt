package ru.sweetmilk.movieapp.cases.movieList.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.HttpResult
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository

class MovieListFragmentViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    private val _movieList = MutableLiveData<PagedResponse<MovieListItem>?>(null)
    val movieList: LiveData<PagedResponse<MovieListItem>?>
        get() = _movieList

    fun loadMovieList() {
        viewModelScope.launch {
            when (val response = movieRepository.getMoviesList()) {
                is HttpResult.Success -> {
                    _movieList.value = response.data
                }

                else -> Unit
            }
        }
    }

    suspend fun loadMovieImage(id: String): Bitmap? =
        movieRepository.getMovieImage(id)
}