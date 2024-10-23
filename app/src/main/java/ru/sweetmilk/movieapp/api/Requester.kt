package ru.sweetmilk.movieapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.sweetmilk.movieapp.api.auth.AuthService
import javax.inject.Inject

class Requester @Inject constructor(private val authService: AuthService) {
    suspend fun <T> send(
        apiMethod: suspend () -> Response<T?>
    ): HttpResponse<T?> = send(apiMethod, true)

    private suspend fun <T> send(
        apiMethod: suspend () -> Response<T?>,
        isNeedToRetryWithTokenUpdate: Boolean
    ): HttpResponse<T?> =
        withContext(Dispatchers.IO) {
            val response = apiMethod.invoke()
            if (response.isSuccessful) {
                HttpResponse.Success(response.body())
            } else if (response.code() == 401 && isNeedToRetryWithTokenUpdate) {
                authService.updateByRefreshToken()
                send(apiMethod, false)
            } else
                HttpResponse.Failed(response.code(), response.toErrorResponse())
        }
}