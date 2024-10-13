package ru.sweetmilk.movieapp.api

import com.google.gson.GsonBuilder

object GsonFactory {

    fun create() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()
}