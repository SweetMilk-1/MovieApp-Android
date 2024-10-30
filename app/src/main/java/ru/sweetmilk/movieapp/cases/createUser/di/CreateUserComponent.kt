package ru.sweetmilk.movieapp.cases.createUser.di

import dagger.Subcomponent
import ru.sweetmilk.movieapp.cases.createUser.CreateUserFragment


@Subcomponent(modules = [CreateUserModule::class])
interface CreateUserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CreateUserComponent
    }

    fun inject(fragment: CreateUserFragment)
}