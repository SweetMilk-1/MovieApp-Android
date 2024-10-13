package ru.sweetmilk.movieapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sweetmilk.movieapp.api.GsonFactory
import ru.sweetmilk.movieapp.api.RetrofitFactory
import ru.sweetmilk.movieapp.api.repositories.movie.MovieApi
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun getMovieApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
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