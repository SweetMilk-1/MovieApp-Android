package ru.sweetmilk.movieapp.cases.userData.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.userData.UserDataFragment

@Subcomponent(modules = [UserDataModule::class])
interface UserDataComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserDataComponent
    }

    fun inject(fragment: UserDataFragment)
}