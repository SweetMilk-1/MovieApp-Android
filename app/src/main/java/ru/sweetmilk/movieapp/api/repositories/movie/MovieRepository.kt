package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.BaseRepository
import ru.sweetmilk.movieapp.api.repositories.HttpResult

private val LOG_TAG = "MovieRepository"

class MovieRepository: BaseRepository()  {
    private var movieApi: MovieApi = retrofit.create(MovieApi::class.java)

    suspend fun getMoviesList(): HttpResult<PagedResponse <MovieListItem>?> = handleApiResponse {
        movieApi.getMovieList()
    }

    suspend fun getMovieImage(id: String): Bitmap? = withContext(Dispatchers.Default) {
        val result = handleApiResponse {
            movieApi.getMovieImage(id)
        }
        when (result) {
            is HttpResult.Success -> BitmapFactory.decodeStream(result.data?.byteStream())
            else -> null
        }
    }
}