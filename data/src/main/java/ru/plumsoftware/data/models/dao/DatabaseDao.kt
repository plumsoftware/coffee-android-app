package ru.plumsoftware.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.LikedDrink

@Dao
interface DatabaseDao {
    @Insert(entity = IntolerableIngredients::class)
    suspend fun insert(intolerableIngredients: IntolerableIngredients)

    @Query("DELETE FROM IntolerableIngredients WHERE ingredientId=:ingredientId")
    suspend fun delete(ingredientId: Int)

    @Query("SELECT * FROM IntolerableIngredients")
    suspend fun getIntolerableIngredients(): List<IntolerableIngredients>?


    @Upsert(entity = LikedDrink::class)
    suspend fun upsert(likedDrink: LikedDrink)

    @Query("DELETE FROM LikedDrink WHERE drinkId=:drinkId")
    suspend fun deleteById(drinkId: Int)

    @Query("SELECT * FROM LikedDrink")
    suspend fun get(): List<LikedDrink>
}