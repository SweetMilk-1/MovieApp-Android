package ru.sweetmilk.movieapp.cases.movieList

import SingleLiveEvent
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.movie.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.movie.MovieListItem
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository
import java.util.UUID
import javax.inject.Inject

class MovieListFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var page: Int = 1
    private var pageCount: Int = INF_PAGE_COUNT

    val isLastPage: Boolean get() = page >= pageCount

    var search: String? = null

    private val _movieListLiveData = MutableLiveData<List<MovieListItem>>()
    val movieListLiveData: LiveData<List<MovieListItem>>
        get() = _movieListLiveData

    private val _failureMessage = MutableLiveData<String>()
    val failureMessage: LiveData<String>
        get() = _failureMessage

    init {
        fetchFirstPage()
    }

    val failedMessageLiveEvent = SingleLiveEvent<String>()

    private fun loadMovieList() {
        viewModelScope.launch {
            try {
                val getMoviesRequest = GetMoviesRequest(
                    page = page,
                    search = search
                )

                when (val response = movieRepository.getMoviesList(getMoviesRequest)) {
                    is HttpResponse.Success -> {
                        page = response.data?.page ?: 1
                        pageCount = response.data?.pageCount ?: 1

                        val newMovieList = _movieListLiveData.value?.toMutableList() ?: mutableListOf()
                        newMovieList.addAll(response.data?.items ?: listOf())
                        _movieListLiveData.value = newMovieList
                    }

                    is HttpResponse.Failed -> {
                        pageCount = page
                        failedMessageLiveEvent.value = response.errorBody.message
                    }
                }
            } catch (e: Throwable) {
                pageCount = page
                Log.e("MovieListFragmentViewModel", e.message, e)
                failedMessageLiveEvent.value = e.message
            }
        }
    }

    fun fetchFirstPage() {
        page = 1
        pageCount = INF_PAGE_COUNT
        _movieListLiveData.value = listOf()
        loadMovieList()
    }

    fun fetchNextPage() {
        if (page == pageCount)
            return
        page++
        loadMovieList()
    }

    suspend fun fetchMovieImage(id: UUID): Drawable? =
        movieRepository.getMovieImage(id)

    companion object {
        private val INF_PAGE_COUNT = 1000000
    }
}