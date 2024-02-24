package ru.plumsoftware.coffeeapp.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState

data class SettingsState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val name: String,
    val age: String,
    val birthday: String,
    val useDark: Boolean,
    val showBottomSheet: Boolean = false,
    val sheetState: SheetState = SheetState(false),
    val agreeDate: Long
)