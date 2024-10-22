package ru.sweetmilk.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthSharedPreferences

    @Singleton
    @AuthSharedPreferences
    @Provides
    fun getAuthSharedPreferences(appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val AUTH_SHARED_PREFERENCES = "AUTH_SHARED_PREFERENCES"
    }
}