package ru.sweetmilk.movieapp.cases.auth

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.auth.AuthService
import ru.sweetmilk.movieapp.cases.auth.AuthFragment.Companion.LOGIN_FIELD
import ru.sweetmilk.movieapp.cases.auth.AuthFragment.Companion.PASSWORD_FIELD
import ru.sweetmilk.movieapp.validation.ValidationError
import ru.sweetmilk.movieapp.validation.Validator
import ru.sweetmilk.movieapp.validation.constraints.MinLengthConstraint
import ru.sweetmilk.movieapp.validation.constraints.NotEmptyConstraint
import ru.sweetmilk.movieapp.validation.constraints.NotNullConstraint
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    var login: String? = null
    var password: String? = null

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val isAuthorizedEvent = SingleLiveEvent<Boolean>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    val constraintsMap = mapOf(
        Pair(
            LOGIN_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                MinLengthConstraint(4)
            )
        ),

        Pair(
            PASSWORD_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                MinLengthConstraint(6)
            )
        )
    )

    fun validate(): Map<String, ValidationError> {
        val fieldsMap = mapOf<String, Any?>(
            Pair(LOGIN_FIELD, login),
            Pair(PASSWORD_FIELD, password)
        )
        return Validator.validate(fieldsMap, constraintsMap)
    }

    fun authorize() {
        _isLoading.value = true
        viewModelScope.launch {
            when (val response = authService.authorize(login!!, password!!)) {
                is HttpResponse.Failed -> _errorMessage.value = response.errorBody.message
                is HttpResponse.Success -> isAuthorizedEvent.value = true
            }
            _isLoading.value = false
        }
    }
}