package ru.plumsoftware.coffeeapp.ui.screens.main

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.User

data class MainState(
    val user: User? = User(),
    val useDark: Boolean = false,
    val targetColorScheme: ColorScheme = LightColors,
    val navColor: Color = targetColorScheme.background,
    val statusBarColor: Color = targetColorScheme.background,
    val currentScreen: Screens.Screens = Screens.Screens.Home
)
