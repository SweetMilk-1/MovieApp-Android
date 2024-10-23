package ru.sweetmilk.movieapp.api.models.auth

data class AuthTokens(
    var accessToken: String,
    var refreshToken: String
)
