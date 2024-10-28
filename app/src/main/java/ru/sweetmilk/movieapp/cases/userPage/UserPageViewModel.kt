package ru.sweetmilk.movieapp.cases.userPage

import androidx.lifecycle.ViewModel
import ru.sweetmilk.movieapp.api.auth.TokenStorage
import java.util.UUID
import javax.inject.Inject

class UserPageViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
): ViewModel() {

    fun getUserId(): UUID? = tokenStorage.getUserId()

}