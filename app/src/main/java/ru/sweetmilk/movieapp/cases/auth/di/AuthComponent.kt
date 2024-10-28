package ru.sweetmilk.movieapp.cases.auth.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.auth.AuthFragment

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(activity: AuthFragment)
}