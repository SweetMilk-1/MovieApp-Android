package ru.sweetmilk.movieapp.cases.movieDetails.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.movieDetails.MovieDetailsFragmentViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class MovieDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsFragmentViewModel::class)
    abstract fun bindViewModel(viewmodel: MovieDetailsFragmentViewModel): ViewModel
}