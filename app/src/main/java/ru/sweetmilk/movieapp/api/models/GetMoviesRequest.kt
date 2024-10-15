package ru.sweetmilk.movieapp.api.models

data class GetMoviesRequest(
    var search: String? = null,
    var page: Int? = null,
    var perPage: Int? = null
)