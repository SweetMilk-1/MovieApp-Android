package ru.sweetmilk.movieapp.api.repositories

import ru.sweetmilk.movieapp.api.models.ErrorResponse

sealed class HttpRequestStatus<out R> {
    data class Success<out T>(val data: T) : HttpRequestStatus<T>()
    data class Failed(val statusCode: Int, val errorBody: ErrorResponse) : HttpRequestStatus<Nothing>()
    object Loading : HttpRequestStatus<Nothing>()
}