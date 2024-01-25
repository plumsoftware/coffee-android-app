package ru.plumsoftware.coffeeapp.ui.screens.search

import ru.plumsoftware.data.models.Coffee

data class SearchState(
    val query: String = "",
    val coffeeMatrix: List<List<Coffee>>,
    val tagArray: Int,
    val tag: String
)
