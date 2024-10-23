package ru.sweetmilk.movieapp.api.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthApi
) : AuthService {
    override suspend fun authorize(login: String, password: String) = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): String = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun updateByRefreshToken() = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }
}