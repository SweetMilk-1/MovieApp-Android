package ru.sweetmilk.movieapp.api.repositories.movie

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sweetmilk.movieapp.api.models.movie.Movie
import ru.sweetmilk.movieapp.api.models.movie.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse

interface MovieApi {
    @GET("/Movie")
    suspend fun getMovieList(
        @Query("search") search: String? = null,
        @Query("page") page: Int? = null,
        @Query("perPage") perPage: Int? = 8
    ): Response<PagedResponse<MovieListItem>?>

    @GET("/Movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId: String): Response<Movie?>


    @GET("/Movie/{movieId}/Photo")
    suspend fun getMovieImage(@Path("movieId") id: String): Response<ResponseBody?>
}