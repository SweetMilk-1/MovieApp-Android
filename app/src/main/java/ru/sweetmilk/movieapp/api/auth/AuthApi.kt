package ru.sweetmilk.movieapp.api.auth

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.sweetmilk.movieapp.api.models.auth.AuthRequest

interface AuthApi {
    @POST("/User/Authentication")
    suspend fun authorize (@Body authRequest: AuthRequest): Response<ResponseBody?>
}