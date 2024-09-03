package ru.plumsoftware.coffeeapp.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.plumsoftware.coffeeapp.di.databaseModule
import ru.plumsoftware.coffeeapp.di.domainModule

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(databaseModule, domainModule))
        }

    }
}