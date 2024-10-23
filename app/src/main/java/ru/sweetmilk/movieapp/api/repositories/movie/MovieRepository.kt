package ru.sweetmilk.movieapp.api.repositories.movie

import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.models.movie.GetMoviesRequest
import ru.sweetmilk.movieapp.api.models.movie.Movie
import ru.sweetmilk.movieapp.api.models.movie.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.Requester
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val requester: Requester
) {
    suspend fun getMoviesList(request: GetMoviesRequest? = null): HttpResponse<PagedResponse<MovieListItem>?> =
        requester.send {
            delay(1000)
            movieApi.getMovieList(
                search = request?.search,
                page = request?.page
            )
        }

    suspend fun getMovie(movieId: UUID?): HttpResponse<Movie?> = requester.send {
        movieApi.getMovie(movieId.toString())
    }

    suspend fun getMovieImage(id: UUID): Drawable? = withContext(Dispatchers.Default) {
        delay(200)
        val result = requester.send {
            movieApi.getMovieImage(id.toString())
        }
        when (result) {
            is HttpResponse.Success -> Drawable.createFromStream(result.data?.byteStream(), null)
            else -> null
        }
    }
}