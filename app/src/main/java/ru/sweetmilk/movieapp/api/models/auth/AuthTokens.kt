package ru.sweetmilk.movieapp.api.models.auth

import java.util.UUID

data class AuthTokens(
    val userId: UUID,
    var accessToken: String,
    var refreshToken: String
)
