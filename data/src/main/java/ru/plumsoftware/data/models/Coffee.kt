package ru.plumsoftware.data.models

import ru.plumsoftware.domain.models.CoffeeModel

data class Coffee(
    override val id: Int,
    override val name: String,
    override val imageResId: Int,
    override val type: String,
    override val isLiked: Int,
    override val roastingLevel: String,
    override val description: String,
    override val ageRating: Int,
    override val ingredients: List<Ingredient>
) : CoffeeModel