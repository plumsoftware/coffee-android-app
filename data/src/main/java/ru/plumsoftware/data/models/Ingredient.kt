package ru.plumsoftware.data.models

import ru.plumsoftware.domain.models.IngredientModel

data class Ingredient(
    override val id: Int,
    override val name: String,
    override val iconId: Int = 0,
) : IngredientModel