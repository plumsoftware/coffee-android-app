package ru.plumsoftware.coffeeapp.ui.screens.coffee

import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.IntolerableIngredients

data class CoffeeState(
    val selectedCoffee: Coffee,
    val intolerableIngredients: MutableList<IntolerableIngredients>,
    val randomCoffeeDrinks: List<Coffee>,
    val age: Int,
    val isInterstitialLoading: Boolean
)
