package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.models.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.BaseRepository
import ru.sweetmilk.movieapp.api.repositories.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
): BaseRepository()  {
    suspend fun getMoviesList(request: GetMoviesRequest? = null): HttpResponse<PagedResponse <MovieListItem>?> = handleApiResponse {
        delay(3000)
        movieApi.getMovieList(
            search = request?.search,
            page = request?.page
        )
    }

    suspend fun getMovieImage(id: String): Bitmap? = withContext(Dispatchers.Default) {
        val result = handleApiResponse {
            movieApi.getMovieImage(id)
        }
        when (result) {
            is HttpResponse.Success -> BitmapFactory.decodeStream(result.data?.byteStream())
            else -> null
        }
    }
}