package ru.plumsoftware.coffeeapp.ui.screens.ingredients

import ru.plumsoftware.data.models.Ingredient

data class IntolerableIngredientsState(
    val list: MutableList<Ingredient>,
    val intolerableIngredients: MutableList<Ingredient>,
    val firstSetup: Boolean
)
