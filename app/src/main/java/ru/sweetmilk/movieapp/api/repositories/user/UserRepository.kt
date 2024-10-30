package ru.sweetmilk.movieapp.api.repositories.user

import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.Requester
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val requester: Requester
) {
    suspend fun getUserData(userId: UUID) = withContext(Dispatchers.IO) {
        requester.send {
            userApi.getUserData(userId)
        }
    }

    suspend fun getUserImage(userId: UUID) = withContext(Dispatchers.IO) {
        val response = requester.send {
            userApi.getUserImage(userId)
        }
        when (response) {
            is HttpResponse.Success -> Drawable.createFromStream(response.data?.byteStream(), null)
            else -> null
        }
    }
}