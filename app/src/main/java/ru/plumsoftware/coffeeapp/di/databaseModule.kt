package ru.plumsoftware.coffeeapp.di

import androidx.room.Room.databaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.domain.constants.Constants

val databaseModule = module {
    single {
        databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            Constants.UserData.DATABASE_NAME
        ).build()
    }

    single { get<UserDatabase>().dao }
}