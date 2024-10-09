package ru.sweetmilk.movieapp.api.repositories

import ru.sweetmilk.movieapp.api.models.ErrorResponse

sealed class HttpResult<out R> {
    data class Success<out T>(val data: T) : HttpResult<T>()
    data class Failed(val statusCode: Int, val errorBody: ErrorResponse) : HttpResult<Nothing>()
}