package ru.plumsoftware.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val drinkId: Int
)
