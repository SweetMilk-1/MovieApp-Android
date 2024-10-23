package ru.sweetmilk.movieapp.api.models.auth

data class AuthRequest(
    val login: String,
    val password: String
)