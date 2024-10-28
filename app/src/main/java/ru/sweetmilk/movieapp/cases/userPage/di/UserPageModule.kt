package ru.sweetmilk.movieapp.cases.userPage.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.userPage.UserPageViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class UserPageModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserPageViewModel::class)
    abstract fun bindViewModel(viewmodel: UserPageViewModel): ViewModel
}