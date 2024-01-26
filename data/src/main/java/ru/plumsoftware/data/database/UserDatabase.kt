package ru.plumsoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.LikedDrink
import ru.plumsoftware.data.models.dao.DatabaseDao

@Database(
    entities = [IntolerableIngredients::class, LikedDrink::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: DatabaseDao
}