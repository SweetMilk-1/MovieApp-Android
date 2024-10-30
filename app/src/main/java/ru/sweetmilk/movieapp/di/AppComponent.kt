package ru.sweetmilk.movieapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.sweetmilk.movieapp.api.di.ApiModule
import ru.sweetmilk.movieapp.cases.auth.di.AuthComponent
import ru.sweetmilk.movieapp.cases.movieDetails.di.MovieDetailsComponent
import ru.sweetmilk.movieapp.cases.movieList.di.MovieListComponent
import ru.sweetmilk.movieapp.cases.userData.di.UserDataComponent
import ru.sweetmilk.movieapp.cases.userPage.di.UserPageComponent
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
    fun addUserPageComponent(): UserPageComponent.Factory
    fun addAuthComponent(): AuthComponent.Factory
    fun addUserDataComponent(): UserDataComponent.Factory
}

@Module(
    subcomponents = [
        MovieListComponent::class,
        MovieDetailsComponent::class,
        UserPageComponent::class,
        AuthComponent::class,
        UserDataComponent::class
    ]
)
object FragmentSubcomponentsModule

