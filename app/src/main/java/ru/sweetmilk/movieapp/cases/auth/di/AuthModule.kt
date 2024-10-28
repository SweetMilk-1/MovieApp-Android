package ru.sweetmilk.movieapp.cases.auth.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.auth.AuthViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class AuthModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindViewModel(viewmodel: AuthViewModel): ViewModel
}