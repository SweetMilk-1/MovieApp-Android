package ru.sweetmilk.movieapp.cases.userAuth.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.userAuth.UserAuthFragment

@Subcomponent(modules = [UserAuthModule::class])
interface UserAuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserAuthComponent
    }

    fun inject(fragment: UserAuthFragment)
}