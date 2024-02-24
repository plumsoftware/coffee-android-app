package ru.plumsoftware.coffeeapp.ui.screens.name

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState

data class NameState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val name: String = "",
    val birthday: String = "",
    val age: String = "",
    val isActive: Boolean = false,
    val showBottomSheet: Boolean = false,
    val sheetState: SheetState = SheetState(false),
    val checkBox: Boolean = false,
)
