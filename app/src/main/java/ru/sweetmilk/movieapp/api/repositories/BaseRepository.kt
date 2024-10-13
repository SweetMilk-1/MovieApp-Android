package ru.sweetmilk.movieapp.api.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.sweetmilk.movieapp.api.toErrorResponse

abstract class BaseRepository(
    val defDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected suspend fun <T> handleApiResponse(apiMethod: suspend () -> Response<T?>): HttpRequestStatus<T?> =
        withContext(defDispatcher) {
            val response = apiMethod.invoke()
            if (response.isSuccessful) {
                HttpRequestStatus.Success(response.body())
            } else {
                HttpRequestStatus.Failed(response.code(), response.toErrorResponse())
            }
        }
}