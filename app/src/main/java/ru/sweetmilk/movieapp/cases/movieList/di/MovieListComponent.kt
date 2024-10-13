package ru.sweetmilk.movieapp.cases.movieList.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.movieList.MovieListFragment


@Subcomponent(modules = [MovieListModule::class])
interface MovieListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieListComponent
    }

    fun inject(fragment: MovieListFragment)
}

