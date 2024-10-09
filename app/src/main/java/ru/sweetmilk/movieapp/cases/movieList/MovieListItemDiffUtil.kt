package ru.sweetmilk.movieapp.cases.movieList

import androidx.recyclerview.widget.DiffUtil
import ru.sweetmilk.movieapp.api.models.MovieListItem

class MovieListItemDiffUtil: DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem == newItem
    }
}