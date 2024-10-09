package ru.sweetmilk.movieapp.api.repositories.movie

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sweetmilk.movieapp.api.models.MovieListItem
import ru.sweetmilk.movieapp.api.models.PagedResponse

interface MovieApi {
    @GET("/Movie")
    fun getMovieList(): Call<PagedResponse<MovieListItem>>

    @GET("/Movie/{movieId}/Photo")
    suspend fun getMovieImage(@Path("movieId") id: String): ResponseBody
}