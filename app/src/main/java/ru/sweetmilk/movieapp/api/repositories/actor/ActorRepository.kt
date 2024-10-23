package ru.sweetmilk.movieapp.api.repositories.actor

import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.Requester
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorRepository @Inject constructor(
    private val actorApi: ActorApi,
    private val requester: Requester
) {
    suspend fun getActorPhoto(id: UUID): Drawable? = withContext(Dispatchers.Default) {
        delay(200)
        val result = requester.send {
            actorApi.getActorPhoto(id.toString())
        }
        when (result) {
            is HttpResponse.Success -> Drawable.createFromStream(result.data?.byteStream(), null)
            else -> null
        }
    }
}