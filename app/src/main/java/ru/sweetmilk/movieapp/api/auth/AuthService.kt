package ru.sweetmilk.movieapp.api.auth


interface  AuthService {
    suspend fun authorize(login: String, password: String)
    suspend fun getAccessToken(): String
    suspend fun updateByRefreshToken()
}




