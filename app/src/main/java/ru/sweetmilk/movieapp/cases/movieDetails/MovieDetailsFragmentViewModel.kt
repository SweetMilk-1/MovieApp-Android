package ru.sweetmilk.movieapp.cases.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.Movie
import ru.sweetmilk.movieapp.api.repositories.HttpResponse
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository
import java.util.UUID
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadMovieDetails(movieId: UUID?): LiveData<Movie?> {

        val liveData = MutableLiveData<Movie>()

        _isLoading.value = true
        viewModelScope.launch {
            val response = movieRepository.getMovie(movieId)

            when (response) {
                is HttpResponse.Success -> liveData.value = response.data
                else -> Unit
            }
            _isLoading.value = false
        }

        return liveData
    }
}