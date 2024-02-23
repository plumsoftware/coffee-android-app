package ru.plumsoftware.coffeeapp.ui.screens.liked

import ru.plumsoftware.data.models.Coffee

data class LikedState(
    val coffeeMatrix: List<List<Coffee>>,
    val query: String = "",
    val tag: String,
    val isAdult: Boolean,
)
