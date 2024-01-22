package ru.plumsoftware.data.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.User

@Dao
interface DatabaseDao {

    @Upsert(User::class)
    suspend fun upsert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User")
    suspend fun getUser(): User?

    @Insert(entity = IntolerableIngredients::class)
    suspend fun insert(intolerableIngredients: IntolerableIngredients)

    @Query("DELETE FROM IntolerableIngredients WHERE ingredientId=:ingredientId")
    suspend fun delete(ingredientId: Int)

    @Query("SELECT * FROM IntolerableIngredients")
    suspend fun getIntolerableIngredients(): List<IntolerableIngredients>?

}