package ru.sweetmilk.movieapp.api.models.user

data class CreateUserRequest(
    var login: String,
    var email: String,
    var password: String
)