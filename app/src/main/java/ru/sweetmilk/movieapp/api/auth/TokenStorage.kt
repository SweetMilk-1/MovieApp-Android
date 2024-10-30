package ru.sweetmilk.movieapp.api.auth

import android.content.SharedPreferences
import ru.sweetmilk.movieapp.di.AppModule.AuthSharedPreferences
import java.util.UUID
import javax.inject.Inject


class TokenStorage @Inject constructor(
    @AuthSharedPreferences private val sharedPreferences: SharedPreferences
) {

    fun getUserId(): UUID? {
        val userIdStr = sharedPreferences.getString(USER_ID_KEY, null)
        return if (userIdStr.isNullOrEmpty()) null else UUID.fromString(userIdStr)
    }

    fun setUserId(userId: UUID) {
        sharedPreferences
            .edit()
            .putString(USER_ID_KEY, userId.toString())
            .apply()
    }

    fun removeUserId() {
        sharedPreferences
            .edit()
            .remove(USER_ID_KEY)
            .apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun setAccessToken(token: String?) {
        sharedPreferences
            .edit()
            .putString(ACCESS_TOKEN_KEY, token)
            .apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    fun setRefreshToken(token: String?) {
        sharedPreferences
            .edit()
            .putString(REFRESH_TOKEN_KEY, token)
            .apply()
    }

    companion object {
        private const val USER_ID_KEY = "USER_ID_KEY"
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}