package ru.plumsoftware.data.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.plumsoftware.data.models.User

@Dao
interface UserModelDao {
    @Upsert(User::class)
    suspend fun upsert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User")
    suspend fun get(): User?


}