package ru.sweetmilk.movieapp.cases.createUser

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sweetmilk.movieapp.api.HttpResponse
import ru.sweetmilk.movieapp.api.repositories.user.UserRepository
import ru.sweetmilk.movieapp.validation.ValidationError
import ru.sweetmilk.movieapp.validation.Validator
import ru.sweetmilk.movieapp.validation.constraints.EmailConstraint
import ru.sweetmilk.movieapp.validation.constraints.MinLengthConstraint
import ru.sweetmilk.movieapp.validation.constraints.NotEmptyConstraint
import ru.sweetmilk.movieapp.validation.constraints.NotNullConstraint
import javax.inject.Inject

class CreateUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var login: String? = null
    var email: String? = null
    var password: String? = null
    var repeatPassword: String? = null

    private val constraintsMap = mapOf(
        Pair(
            CreateUserFragment.LOGIN_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                MinLengthConstraint(4)
            )
        ),
        Pair(
            CreateUserFragment.EMAIL_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                EmailConstraint,
            )
        ),
        Pair(
            CreateUserFragment.PASSWORD_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                MinLengthConstraint(6)
            )
        ),
        Pair(
            CreateUserFragment.REPEAT_PASSWORD_FIELD, arrayOf(
                NotNullConstraint,
                NotEmptyConstraint,
                MinLengthConstraint(6)
            )
        )
    )

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    val userCreatedEvent = SingleLiveEvent<Boolean>()

    fun validate(): Map<String, ValidationError> {
        val fieldsMap = mapOf<String, Any?>(
            Pair(CreateUserFragment.LOGIN_FIELD, login),
            Pair(CreateUserFragment.EMAIL_FIELD, email),
            Pair(CreateUserFragment.PASSWORD_FIELD, password),
            Pair(CreateUserFragment.REPEAT_PASSWORD_FIELD, repeatPassword),
        )
        return Validator.validate(fieldsMap, constraintsMap)
    }

    fun isPasswordsSame(errorMessage: String):Boolean {
        if (password != repeatPassword)
        {
            _errorMessage.value = errorMessage
            return false
        }
        return true
    }

    fun createUser() {
        _isLoading.value = true
        viewModelScope.launch {
            when (val response = userRepository.creteUser(login!!, email!!, password!!)) {
                is HttpResponse.Success -> userCreatedEvent.value = true
                is HttpResponse.Failed -> _errorMessage.value = response.errorBody.message
            }
            _isLoading.value = false
        }
    }
}