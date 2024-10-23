package ru.sweetmilk.movieapp.api

import okhttp3.Interceptor
import ru.sweetmilk.movieapp.api.auth.TokenStorage
import javax.inject.Inject

class AddAuthHeaderInterceptor @Inject constructor(private val tokenStorage: TokenStorage) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest = chain.request().newBuilder().apply {
            val token = tokenStorage.getAccessToken()
            if (!token.isNullOrEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(newRequest)
    }
}