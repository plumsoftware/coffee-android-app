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

    @Query("INSERT INTO User (id, name, birthday, theme, isFirst) VALUES (0, :name, :birthday, :theme, :isFirst);")
    suspend fun insert(name: String, birthday: Long, theme: Boolean, isFirst: Int)

    @Query("DELETE FROM User WHERE id = 0")
    suspend fun deleteUser()

    @Query("SELECT * FROM User WHERE id = 0")
    suspend fun getUser(): User?

    @Upsert(User::class)
    suspend fun upsert(user: User)

    @Query("UPDATE User SET theme=:theme WHERE id = 0")
    suspend fun updateTheme(theme: Boolean)

    @Query("UPDATE User SET name=:name WHERE id = 0")
    suspend fun updateName(name: String)

    @Query("UPDATE User SET birthday=:birthday WHERE id = 0")
    suspend fun updateBirthday(birthday: Long)


    @Insert(entity = IntolerableIngredients::class)
    suspend fun insert(intolerableIngredients: IntolerableIngredients)

    @Query("DELETE FROM IntolerableIngredients WHERE ingredientId=:ingredientId")
    suspend fun delete(ingredientId: Int)

    @Query("SELECT * FROM IntolerableIngredients")
    suspend fun getIntolerableIngredients(): List<IntolerableIngredients>?

}