package ru.sweetmilk.movieapp.api.models

data class PagedResponse<T>(
    var page: Int,
    var totalCount: Int,
    var pageCount: Int,
    var perPage: Int,
    var items: List<T>
)
