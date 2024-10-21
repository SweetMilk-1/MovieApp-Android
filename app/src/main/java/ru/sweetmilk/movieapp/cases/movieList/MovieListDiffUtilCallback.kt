package ru.sweetmilk.movieapp.cases.movieList

import androidx.recyclerview.widget.DiffUtil
import ru.sweetmilk.movieapp.api.models.MovieListItem

class MovieListDiffUtilCallback(
    private val oldList: List<MovieListItem>,
    private val newList: List<MovieListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[oldItemPosition] == newList[newItemPosition]
}