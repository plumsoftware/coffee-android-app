package ru.plumsoftware.data.models

import ru.plumsoftware.domain.models.CoffeeModel

data class Coffee(
    override val id: Int,
    override val name: String,
    override val history: String,
    override val roastingLevel: String,
    override val tastes: String,
    override val cookingMethod: String,
    override val description: String,
    override val ageRating: String,
    override val ingredients: List<Ingredient>
) : CoffeeModel