package ru.sweetmilk.movieapp.cases.logIn.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.logIn.LogInViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class LogInModule {
    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun bindViewModel(viewmodel: LogInViewModel): ViewModel
}