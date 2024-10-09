package ru.sweetmilk.movieapp.api.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.sweetmilk.movieapp.api.RetrofitFactory
import ru.sweetmilk.movieapp.api.toErrorResponse

abstract class BaseRepository(
    val defDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected val retrofit = RetrofitFactory.create()

    protected suspend fun <T> handleApiResponse(apiMethod: suspend () -> Response<T?>): HttpResult<T?> = withContext(defDispatcher) {
        val response = apiMethod.invoke()
        if (response.isSuccessful) {
            HttpResult.Success(response.body())
        }
        else {
            HttpResult.Failed(response.code(), response.toErrorResponse())
        }
    }
}