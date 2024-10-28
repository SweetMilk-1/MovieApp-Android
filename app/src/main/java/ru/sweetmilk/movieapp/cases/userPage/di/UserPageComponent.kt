package ru.sweetmilk.movieapp.cases.userPage.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.userPage.UserPageFragment

@Subcomponent(modules = [UserPageModule::class])
interface UserPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserPageComponent
    }

    fun inject(fragment: UserPageFragment)
}