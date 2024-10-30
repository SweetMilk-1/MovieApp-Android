package ru.sweetmilk.movieapp.api.repositories.user

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.sweetmilk.movieapp.api.models.user.CreateUserRequest
import ru.sweetmilk.movieapp.api.models.user.User
import java.util.UUID

interface UserApi {
    @GET("/User/{userId}")
    suspend fun getUserData(@Path("userId") userId: UUID): Response<User?>

    @POST("/User")
    suspend fun createUser(@Body user: CreateUserRequest): Response<UUID?>

    @GET("/User/{userId}/Photo")
    suspend fun getUserImage(@Path("userId") userId: UUID): Response<ResponseBody?>
}