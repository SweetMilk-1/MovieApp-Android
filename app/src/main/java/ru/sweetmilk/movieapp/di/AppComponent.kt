package ru.sweetmilk.movieapp.di

import dagger.Component
import dagger.Module
import ru.sweetmilk.movieapp.api.repositories.movie.MovieApi
import ru.sweetmilk.movieapp.cases.movieList.di.MovieListComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelBuilderModule::class,
    FragmentSubcomponentsModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun addMovieListComponent() : MovieListComponent.Factory

    val movieApi: MovieApi
}

@Module(subcomponents = [
    MovieListComponent::class
])
object FragmentSubcomponentsModule