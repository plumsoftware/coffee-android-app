package ru.plumsoftware.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.plumsoftware.domain.models.LikedDrinkModel

@Entity
data class LikedDrink(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val drinkId: Int
) : LikedDrinkModel
