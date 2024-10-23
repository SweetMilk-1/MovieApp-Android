package ru.sweetmilk.movieapp.api.auth

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.sweetmilk.movieapp.api.models.auth.AuthRequest
import ru.sweetmilk.movieapp.api.models.auth.AuthTokens
import ru.sweetmilk.movieapp.api.toErrorResponse
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton

private val LOG_TAG = "AuthServiceImpl"

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
        if (refreshToken != null) {
            val response = authApi.updateTokens(refreshToken)
            handleAuthResponseAndStoreTokens(response)
        } else false
    }

    private fun handleAuthResponseAndStoreTokens(response: Response<AuthTokens>) =
        try {
            if (response.isSuccessful) {
                val tokens = response.body()
                    ?: throw InvalidObjectException("Auth failed because server did return null object")
                tokenStorage.setAccessToken(tokens.accessToken)
                tokenStorage.setRefreshToken(tokens.refreshToken)
                true
            } else {
                throw InvalidObjectException("Request Fail: " + response.toErrorResponse().message)
            }
        } catch (e: Throwable) {
            Log.e(LOG_TAG, e.message, e)
            false
        }
}