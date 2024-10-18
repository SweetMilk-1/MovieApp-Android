package ru.sweetmilk.movieapp.cases.movieDetails

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.models.DictionaryItem
import ru.sweetmilk.movieapp.databinding.HolderActorInMovieDetailsBinding
import java.util.UUID

class ActorsInMovieDetailsAdapter(
    private val inflater: LayoutInflater,
    private val actors: List<DictionaryItem>,
    private val coroutineScope: CoroutineScope,
    private val imageLoader: suspend (actorId: UUID)->Drawable?
): RecyclerView.Adapter<ActorsInMovieDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsInMovieDetailsViewHolder {
        val binding = HolderActorInMovieDetailsBinding.inflate(inflater, parent, false)
        return ActorsInMovieDetailsViewHolder(binding)
    }

    override fun getItemCount(): Int = actors.size

    override fun onBindViewHolder(holder: ActorsInMovieDetailsViewHolder, position: Int) {
        val actor = actors[position]
        holder.bind(actor)
        coroutineScope.launch {
            val drawable = imageLoader.invoke(actor.id)
            holder.bindImage(drawable)
        }
    }
}

