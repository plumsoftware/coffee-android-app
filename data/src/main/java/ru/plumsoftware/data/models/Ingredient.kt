package ru.plumsoftware.data.models

import ru.plumsoftware.domain.models.IngredientModel

data class Ingredient(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val measure: String
) : IngredientModel