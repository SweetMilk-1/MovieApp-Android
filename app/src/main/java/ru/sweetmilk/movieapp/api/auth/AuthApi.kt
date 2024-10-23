package ru.sweetmilk.movieapp.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.sweetmilk.movieapp.api.models.auth.AuthRequest
import ru.sweetmilk.movieapp.api.models.auth.AuthTokens

interface AuthApi {
    @POST("/User/Authentication")
    suspend fun authorize (@Body authRequest: AuthRequest): Response<AuthTokens>

    @POST("/User/UpdateTokens")
    suspend fun updateTokens (@Query("refreshToken") refreshToken: String): Response<AuthTokens>
}