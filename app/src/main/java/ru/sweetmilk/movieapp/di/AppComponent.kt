package ru.sweetmilk.movieapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.movieapp.api.di.ApiModule
import ru.sweetmilk.movieapp.cases.movieDetails.di.MovieDetailsComponent
import ru.sweetmilk.movieapp.cases.movieList.di.MovieListComponent
import ru.sweetmilk.movieapp.cases.userAuth.di.UserAuthComponent
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
    fun addUserAuthComponent(): UserAuthComponent.Factory
}

@Module(
    subcomponents = [
        MovieListComponent::class,
        MovieDetailsComponent::class,
        UserAuthComponent::class
    ]
)
object FragmentSubcomponentsModule

