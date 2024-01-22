package ru.plumsoftware.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.plumsoftware.data.models.IntolerableIngredients

@Dao
interface IntolerableIngredientsDao {

    @Insert(entity = IntolerableIngredients::class)
    suspend fun insert(intolerableIngredients: IntolerableIngredients)

    @Query("DELETE FROM IntolerableIngredients WHERE ingredientId=:ingredientId")
    suspend fun delete(ingredientId: Int)

    @Query("SELECT * FROM IntolerableIngredients")
    suspend fun get(): List<IntolerableIngredients>?

}