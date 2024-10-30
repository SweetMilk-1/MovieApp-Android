package ru.sweetmilk.movieapp.api.models.user

import java.util.UUID

data class User(
    var id: UUID,
    var email: String,
    var isAdmin: Boolean,
    var login: String,
    var createDate: String
)