package ru.sweetmilk.movieapp.cases.userAuth.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.sweetmilk.movieapp.cases.userAuth.UserAuthFragmentViewModel
import ru.sweetmilk.movieapp.di.ViewModelKey

@Module
abstract class UserAuthModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserAuthFragmentViewModel::class)
    abstract fun bindViewModel(viewmodel: UserAuthFragmentViewModel): ViewModel
}