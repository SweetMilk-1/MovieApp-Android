package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.models.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.Movie
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.repositories.BaseRepository
import ru.sweetmilk.movieapp.api.repositories.HttpResponse
import java.util.UUID
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

    suspend fun getMovie(movieId: UUID?): HttpResponse<Movie?> = handleApiResponse {
        movieApi.getMovie(movieId.toString())
    }

    suspend fun getMovieImage(id: UUID): Drawable? = withContext(Dispatchers.Default) {
        delay(1000)
        val result = handleApiResponse {
            movieApi.getMovieImage(id.toString())
        }
        when (result) {
            is HttpResponse.Success -> Drawable.createFromStream(result.data?.byteStream(), null)
            else -> null
        }
    }
}