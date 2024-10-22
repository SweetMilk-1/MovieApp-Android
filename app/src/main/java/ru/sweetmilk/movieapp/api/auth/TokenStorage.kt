package ru.sweetmilk.movieapp.api.auth

import android.content.SharedPreferences
import ru.sweetmilk.movieapp.di.AppModule.AuthSharedPreferences
import javax.inject.Inject


class TokenStorage @Inject constructor(
    @AuthSharedPreferences private val sharedPreferences: SharedPreferences
) {
    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun setAccessToken(token: String) {
        sharedPreferences
            .edit()
            .putString(ACCESS_TOKEN_KEY, token)
            .apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun setRefreshToken(token: String) {
        sharedPreferences
            .edit()
            .putString(REFRESH_TOKEN_KEY, token)
            .apply()
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}