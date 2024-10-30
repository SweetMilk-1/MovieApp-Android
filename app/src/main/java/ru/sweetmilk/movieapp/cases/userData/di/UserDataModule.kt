package ru.sweetmilk.movieapp.cases.userData.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.userData.UserDataViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class UserDataModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserDataViewModel::class)
    abstract fun bindViewModel(viewmodel: UserDataViewModel): ViewModel
}