package ru.sweetmilk.movieapp.cases.logIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.sweetmilk.movieapp.R
import ru.sweetmilk.movieapp.api.auth.AuthService
import javax.inject.Inject

sealed class ErrorType {
    object IsRequired: ErrorType()
    class IsTooShort(val minLength: Int): ErrorType()
}

class LogInViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _errorsLiveData = MutableLiveData<Map<Int, String>>()
    val errorsLiveData: LiveData<Map<Int, String>> get() = _errorsLiveData

    fun auth(login: String?, password: String?) {
        val isValid = validateParams(login, password)
    }

    private fun validateParams(login: String?, password: String?): Boolean {
        val errors = mutableMapOf<Int, String>()
        if (login.isNullOrEmpty()) {
            errors[R.id.login_field] = "Это поле обязательное"
        }

        return false
    }
}