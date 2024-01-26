package ru.plumsoftware.coffeeapp.ui.screens.search

import androidx.compose.ui.focus.FocusRequester
import ru.plumsoftware.data.models.Coffee

data class SearchState(
    val query: String = "",
    val coffeeMatrix: List<List<Coffee>>,
    val tagArray: Int,
    val tag: String,
    val focusRequester: FocusRequester = FocusRequester()
)
