package ru.sweetmilk.movieapp.api.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sweetmilk.movieapp.api.GsonFactory
import ru.sweetmilk.movieapp.api.RetrofitFactory
import ru.sweetmilk.movieapp.api.repositories.actor.ActorApi
import ru.sweetmilk.movieapp.api.repositories.movie.MovieApi
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun getMovieApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun getActorApi(
        retrofit: Retrofit
    ): ActorApi {
        return retrofit.create(ActorApi::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofit(
        gson: Gson
    ): Retrofit {
        return RetrofitFactory.create(gson)
    }

    @Provides
    fun getGson(): Gson {
        return GsonFactory.create()
    }
}