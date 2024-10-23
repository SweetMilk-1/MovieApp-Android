package ru.sweetmilk.movieapp.api.models.movie

import ru.sweetmilk.movieapp.api.models.DictionaryItem
import java.util.Date
import java.util.UUID

data class MovieListItem(
    var id : UUID,
    var title: String,
    var pgInfo: String?,
    var releaseDate: Date,
    var genres: List<DictionaryItem>
)
