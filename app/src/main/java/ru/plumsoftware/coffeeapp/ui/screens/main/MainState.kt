package ru.plumsoftware.coffeeapp.ui.screens.main

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.User

data class MainState(
    val user: User = User(),
    val useDark: Boolean = user.theme,
    val targetColorScheme: ColorScheme = if (user.theme) DarkColors else LightColors,
    val name: String = "",
    val navColor: Color = targetColorScheme.background,
    val statusBarColor: Color = targetColorScheme.background,
    val currentScreen: Screens.Screens = Screens.Screens.Home,
)
