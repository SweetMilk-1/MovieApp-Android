package ru.sweetmilk.movieapp.cases.userData

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.auth.AuthService
import ru.sweetmilk.movieapp.api.models.user.User
import ru.sweetmilk.movieapp.api.repositories.user.UserRepository
import java.util.UUID
import javax.inject.Inject

class UserDataViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepository: UserRepository
): ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadUserData(userId: UUID): LiveData<User?> {

        val liveData = MutableLiveData<User>()

        _isLoading.value = true
        viewModelScope.launch {
            val response = userRepository.getUserData(userId)

            when (response) {
                is HttpResponse.Success -> liveData.value = response.data
                else -> Unit
            }
            _isLoading.value = false
        }

        return liveData
    }

    suspend fun loadUserImage(id: UUID): Drawable? =
        userRepository.getUserImage(id)

    fun logOut() =  authService.logOut()
}