package ru.sweetmilk.movieapp.api

import ru.sweetmilk.movieapp.api.models.ErrorResponse

interface HttpCallbacks {
    fun onStartLoading() {}
    fun onFinishLoading() {}
    fun onRequestFailure(errorResponse: ErrorResponse) {}
    fun onError() {}
}