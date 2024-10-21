package ru.sweetmilk.movieapp.api.models

data class GetMoviesRequest(
    var search: String? = null,
    var page: Int = 1
)