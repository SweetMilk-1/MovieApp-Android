package ru.sweetmilk.movieapp.cases.createUser.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.createUser.CreateUserViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class CreateUserModule {
    @Binds
    @IntoMap
    @ViewModelKey(CreateUserViewModel::class)
    abstract fun bindViewModel(viewmodel: CreateUserViewModel): ViewModel
}