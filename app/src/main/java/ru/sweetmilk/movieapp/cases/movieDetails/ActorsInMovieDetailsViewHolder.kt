package ru.sweetmilk.movieapp.cases.movieDetails

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.models.DictionaryItem
import ru.sweetmilk.movieapp.databinding.HolderActorInMovieDetailsBinding

class ActorsInMovieDetailsViewHolder(private val binding: HolderActorInMovieDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var actor: DictionaryItem? = null

    fun bind(actor: DictionaryItem) {
        this.actor = actor

        binding.movieActorName.text = actor.name
    }

    fun bindImage(actorImage: Drawable?) {
        val drawable = actorImage ?: ResourcesCompat.getDrawable(itemView.resources, R.drawable.no_photo, null)
        binding.movieActorImage.setImageDrawable(drawable)
    }
}