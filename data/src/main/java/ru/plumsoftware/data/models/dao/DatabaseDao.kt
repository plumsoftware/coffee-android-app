package ru.plumsoftware.data.models.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.LikedDrink

@Dao
interface DatabaseDao {
    @Upsert
    suspend fun upsert(intolerableIngredients: IntolerableIngredients)

    @Query("DELETE FROM IntolerableIngredients WHERE ingredientId=:ingredientId")
    suspend fun delete(ingredientId: Int)

    @Query("SELECT * FROM IntolerableIngredients")
    suspend fun getIntolerableIngredients(): List<IntolerableIngredients>?

    @Query("DELETE FROM IntolerableIngredients")
    suspend fun deleteAllIntolerableIngredients()


    @Upsert(entity = LikedDrink::class)
    suspend fun upsert(likedDrink: LikedDrink)

    @Query("DELETE FROM LikedDrink WHERE drinkId=:drinkId")
    suspend fun deleteById(drinkId: Int)

    @Query("SELECT * FROM LikedDrink")
    suspend fun get(): List<LikedDrink>
}