package ru.plumsoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.User
import ru.plumsoftware.data.models.dao.DatabaseDao

@Database(
    entities = [User::class, IntolerableIngredients::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: DatabaseDao
}