package ru.sweetmilk.movieapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.movieapp.api.di.ApiModule
import ru.sweetmilk.movieapp.cases.logIn.di.LogInComponent
import ru.sweetmilk.movieapp.cases.movieDetails.di.MovieDetailsComponent
import ru.sweetmilk.movieapp.cases.movieList.di.MovieListComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ViewModelBuilderModule::class,
        FragmentSubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun addMovieListComponent(): MovieListComponent.Factory
    fun addMovieDetailsComponent(): MovieDetailsComponent.Factory
    fun logInComponent(): LogInComponent.Factory
}

@Module(
    subcomponents = [
        MovieListComponent::class,
        MovieDetailsComponent::class,
        LogInComponent::class
    ]
)
object FragmentSubcomponentsModule

