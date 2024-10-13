package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.BaseRepository
import ru.sweetmilk.movieapp.api.repositories.HttpRequestStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
): BaseRepository()  {
    suspend fun getMoviesList(): HttpRequestStatus<PagedResponse <MovieListItem>?> = handleApiResponse {
        movieApi.getMovieList()
    }

    suspend fun getMovieImage(id: String): Bitmap? = withContext(Dispatchers.Default) {
        val result = handleApiResponse {
            movieApi.getMovieImage(id)
        }
        when (result) {
            is HttpRequestStatus.Success -> BitmapFactory.decodeStream(result.data?.byteStream())
            else -> null
        }
    }
}