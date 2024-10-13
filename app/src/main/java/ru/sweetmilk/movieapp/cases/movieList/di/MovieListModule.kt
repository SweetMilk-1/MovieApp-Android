package ru.sweetmilk.movieapp.cases.movieList.di

import androidx.lifecycle.ViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.movieList.MovieListFragmentViewModel

@Module
abstract class MovieListModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListFragmentViewModel::class)
    abstract fun bindViewModel(viewmodel: MovieListFragmentViewModel): ViewModel
}
