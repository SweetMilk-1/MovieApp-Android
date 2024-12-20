package ru.sweetmilk.movieapp.api.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.models.auth.AuthRequest
import ru.sweetmilk.movieapp.api.models.auth.AuthTokens
import ru.sweetmilk.movieapp.api.toErrorResponse
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthApi
) {
    suspend fun authorize(login: String, password: String) = withContext(Dispatchers.IO) {
        val response = authApi.authorize(AuthRequest(login, password))
        handleAuthResponseAndStoreTokens(response)
    }

    suspend fun updateByRefreshToken() = withContext(Dispatchers.IO) {
        val refreshToken = tokenStorage.getRefreshToken()

        val response = authApi.updateTokens(refreshToken ?: "")
        handleAuthResponseAndStoreTokens(response)
    }

    private fun handleAuthResponseAndStoreTokens(response: Response<AuthTokens>): HttpResponse<Nothing?> {
        if (response.isSuccessful) {
            val tokens = response.body()
                ?: throw InvalidObjectException("Auth failed because server did return null object")
            tokenStorage.setUserId(tokens.userId)
            tokenStorage.setAccessToken(tokens.accessToken)
            tokenStorage.setRefreshToken(tokens.refreshToken)
            return HttpResponse.Success(null)
        } else {
            return HttpResponse.Failed(response.code(), response.toErrorResponse())
        }
    }

    fun logOut() {
        tokenStorage.removeUserId()
        tokenStorage.setAccessToken(null)
        tokenStorage.setRefreshToken(null)
    }
}