package ru.sweetmilk.movieapp.api.repositories.actor

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorApi {
    @GET("/Actor/{actorId}/Photo")
    suspend fun getActorPhoto (@Path("actorId") id: String): Response<ResponseBody?>
}