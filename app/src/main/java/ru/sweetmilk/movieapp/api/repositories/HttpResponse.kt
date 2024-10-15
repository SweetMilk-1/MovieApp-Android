package ru.sweetmilk.movieapp.api.repositories

import ru.sweetmilk.movieapp.api.models.ErrorResponse

sealed class HttpResponse<out R> {
    data class Success<out T>(val data: T) : HttpResponse<T>()
    data class Failed(val statusCode: Int, val errorBody: ErrorResponse) : HttpResponse<Nothing>()
}