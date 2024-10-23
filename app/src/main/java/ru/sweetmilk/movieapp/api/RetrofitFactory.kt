package ru.sweetmilk.movieapp.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sweetmilk.movieapp.api.models.ErrorResponse

object RetrofitFactory {
    val BASE_URL = "http://192.168.0.15/"

    fun create(gson: Gson, addAuthHeaderInterceptor: AddAuthHeaderInterceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(addAuthHeaderInterceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }
}

fun Response<*>.toErrorResponse(): ErrorResponse {
    return GsonFactory.create().fromJson(this.errorBody()?.string(), ErrorResponse::class.java)
}