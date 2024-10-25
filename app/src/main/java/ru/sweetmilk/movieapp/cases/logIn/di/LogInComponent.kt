package ru.sweetmilk.movieapp.cases.logIn.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.logIn.LogInFragment

@Subcomponent(modules = [LogInModule::class])
interface LogInComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LogInComponent
    }

    fun inject(fragment: LogInFragment)
}