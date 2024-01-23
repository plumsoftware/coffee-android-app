package ru.plumsoftware.coffeeapp.dto

import androidx.compose.ui.graphics.painter.Painter
import ru.plumsoftware.coffeeapp.ui.screens.Screens

data class BottomBarDto(
    val name: String,
    val screens: String,
    val painter: Painter
)
