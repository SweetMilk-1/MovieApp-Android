package ru.sweetmilk.movieapp.api.models.movie

data class GetMoviesRequest(
    var search: String? = null,
    var page: Int = 1
)