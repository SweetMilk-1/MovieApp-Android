package ru.sweetmilk.movieapp.cases.movieDetails.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.movieDetails.MovieDetailsFragment

@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailsComponent
    }

    fun inject(fragment: MovieDetailsFragment)
}