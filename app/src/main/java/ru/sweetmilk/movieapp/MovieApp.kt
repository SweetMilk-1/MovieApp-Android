package ru.sweetmilk.movieapp

import android.app.Application
import ru.sweetmilk.movieapp.di.AppComponent
import ru.sweetmilk.movieapp.di.DaggerAppComponent

open class MovieApp : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }
}