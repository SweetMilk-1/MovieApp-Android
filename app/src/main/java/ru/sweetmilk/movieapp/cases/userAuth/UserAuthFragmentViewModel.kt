package ru.sweetmilk.movieapp.cases.userAuth

import SingleLiveEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.auth.AuthService
import javax.inject.Inject

class UserAuthFragmentViewModel @Inject constructor(private val authService: AuthService) :
    ViewModel() {

    val snackBarLiveEvent = SingleLiveEvent<String>()

    fun authorize() {
        viewModelScope.launch {
            val isAuthorized = authService.authorize(LOGIN, PASSWORD)
            val message = if (isAuthorized) "Авторизован" else "Ошибка"
            snackBarLiveEvent.value = message
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val isAuthorized = authService.updateByRefreshToken()
            val message = if (isAuthorized) "Авторизован" else "Ошибка"
            snackBarLiveEvent.value = message
        }
    }

    companion object {
        private const val LOGIN = "sweetmilk"
        private const val PASSWORD = "CZ75Auto"
    }
}