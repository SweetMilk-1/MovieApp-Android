package ru.sweetmilk.movieapp.api

import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sweetmilk.movieapp.api.models.ErrorResponse

object RetrofitFactory {
    val BASE_URL = "http://192.168.0.15:55002/"

    fun create(gson: Gson) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()
}

fun Response<*>.toErrorResponse(): ErrorResponse {
    return GsonFactory.create().fromJson(this.errorBody()?.string(), ErrorResponse::class.java)
}