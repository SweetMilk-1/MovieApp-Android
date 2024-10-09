package ru.sweetmilk.movieapp.cases.movieList.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ru.sweetmilk.movieapp.api.models.MovieListItem
import java.util.Calendar


class MovieListItemViewModel : BaseObservable() {
    var movieListItem: MovieListItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val id: String? get() = movieListItem?.id

    @get:Bindable
    val title: String? get() = movieListItem?.title

    @get:Bindable
    val pgInfo: String? get() = movieListItem?.pgInfo

    @get:Bindable
    val releaseYear: Int?
        get() {
            if (movieListItem?.releaseDate == null) {
                return null
            }
            val cal: Calendar = Calendar.getInstance()
            cal.setTime(movieListItem?.releaseDate!!)
            return cal.get(Calendar.YEAR)
        }

    @get:Bindable
    val genres: String?
        get() = movieListItem?.genres?.joinToString(transform = { it.name })?.lowercase()
}