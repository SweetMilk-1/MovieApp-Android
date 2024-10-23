package ru.sweetmilk.movieapp.api.models.movie

import ru.sweetmilk.movieapp.api.models.DictionaryItem
import java.util.Date
import java.util.UUID

data class Movie(
    var id: UUID,
    var title: String,
    var description: String,
    var pgInfo: String,
    var durationInMinutes: Int,
    var releaseDate: Date,
    var actors: List<DictionaryItem>,
    var genres: List<DictionaryItem>
)