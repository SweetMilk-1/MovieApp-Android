package ru.sweetmilk.movieapp.api.models

import java.util.Date

data class MovieListItem(
    var id : String,
    var title: String,
    var pgInfo: String?,
    var releaseDate: Date,
    var genres: List<DictionaryItem>
)
