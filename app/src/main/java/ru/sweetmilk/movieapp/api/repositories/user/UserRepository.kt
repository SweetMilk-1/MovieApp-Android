package ru.sweetmilk.movieapp.api.repositories.user

import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.Requester
import ru.sweetmilk.movieapp.api.models.user.CreateUserRequest
import java.util.UUID
import javax.inject.Inject
import kotlin.math.log

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

    suspend fun creteUser(login: String, email: String, password: String) =
        withContext(Dispatchers.IO) {
            val createUserRequest = CreateUserRequest(login, email, password)
            requester.send {
                userApi.createUser(createUserRequest)
            }
        }
}