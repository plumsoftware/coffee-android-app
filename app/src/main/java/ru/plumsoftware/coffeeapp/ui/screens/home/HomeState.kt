package ru.plumsoftware.coffeeapp.ui.screens.home

import ru.plumsoftware.data.models.Coffee

data class HomeState(
    val coffeeMatrix: List<List<Coffee>>,
    val coffeeOfTheDay: Coffee,
    val name: String,
    val welcome: Int,
    val isAdult: Boolean,
)
