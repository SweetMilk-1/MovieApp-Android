package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.ErrorResponse
import ru.sweetmilk.movieapp.api.models.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.HttpResponse
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository
import java.util.UUID
import javax.inject.Inject

sealed class MovieListFragmentState {
    object Idle: MovieListFragmentState()
    object Loading : MovieListFragmentState()
    class Success(val data: PagedResponse<MovieListItem>?) : MovieListFragmentState()
    class Failed(val errorCode: Int, val errorData: ErrorResponse) : MovieListFragmentState()
    class Error(val e: Throwable) : MovieListFragmentState()
}



class MovieListFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var request = GetMoviesRequest()

    private val _movieListFragmentState = MutableLiveData<MovieListFragmentState>(MovieListFragmentState.Idle)
    val movieListFragmentState: LiveData<MovieListFragmentState>
        get() = _movieListFragmentState

    init {
        loadMovieList()
    }

    fun loadMovieList() {
        _movieListFragmentState.value = MovieListFragmentState.Loading
        viewModelScope.launch {
            try {
                when (val response = movieRepository.getMoviesList(request)) {
                    is HttpResponse.Success -> {
                        _movieListFragmentState.value = MovieListFragmentState.Success(response.data)
                    }
                    is HttpResponse.Failed -> {
                        _movieListFragmentState.value = MovieListFragmentState.Failed(response.statusCode, response.errorBody)
                    }
                }
            }
            catch (e: Throwable){
                _movieListFragmentState.value = MovieListFragmentState.Error(e)
            }
        }
    }

    fun setSearchText(search: String?) {
        request.search = search
    }

    fun setCurrentPage(page: Int) {
        request.page = page
    }

    suspend fun loadMovieImage(id: UUID): Bitmap? =
        movieRepository.getMovieImage(id)

}