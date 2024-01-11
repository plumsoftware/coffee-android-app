package ru.plumsoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.plumsoftware.data.models.User
import ru.plumsoftware.data.models.dao.UserModelDao

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase :  RoomDatabase() {
    abstract val dao: UserModelDao
}