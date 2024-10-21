package ru.sweetmilk.movieapp.cases.movieList

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.repositories.HttpResponse
import ru.sweetmilk.movieapp.api.repositories.movie.MovieRepository
import java.util.UUID
import javax.inject.Inject

class MovieListFragmentViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    var page: Int = 1
        private set

    var pageCount: Int = INF_PAGE_COUNT
        private set

    val isLastPage: Boolean get() = page >= pageCount

    var search: String? = null

    private val _movieListLiveData = MutableLiveData<List<MovieListItem>>()
    val movieListLiveData: LiveData<List<MovieListItem>>
        get() = _movieListLiveData

    init {
        fetchFirstPage()
    }

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
                        //TODO отобразить в снекбаре что произошла ошибка
                    }
                }
            } catch (e: Throwable) {
                Log.e("MovieListFragmentViewModel", e.message, e)
                throw e
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