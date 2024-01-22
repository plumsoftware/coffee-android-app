package ru.plumsoftware.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IntolerableIngredients(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ingredientId: Int = 0,
)
