package ru.plumsoftware.coffeeapp.ui.screens.main

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class MainViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : ViewModel() {

    val state = MutableStateFlow(MainState(
        user = User(
            name = sharedPreferencesStorage.get().name,
            birthday = sharedPreferencesStorage.get().birthday,
            theme = sharedPreferencesStorage.get().theme,
            isFirst = sharedPreferencesStorage.get().isFirst,
        )
    ))

    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeColorScheme -> {
                state.update {
                    it.copy(
                        useDark = event.useDark,
                        targetColorScheme = event.targetColorScheme,
                        navColor = event.targetColorScheme.background,
                        statusBarColor = event.targetColorScheme.background
                    )
                }
            }

            is Event.SetTheme -> {
                state.update {
                    it.copy(
                        user = event.user!!,
                        useDark = event.user.theme,
                        targetColorScheme = if (event.user.theme) DarkColors else LightColors,
                        navColor = if (event.user.theme) DarkColors.background else LightColors.background,
                        statusBarColor = if (event.user.theme) DarkColors.background else LightColors.background
                    )
                }
            }

            is Event.ChangeStatusBarColor -> {
                state.update {
                    it.copy(
                        statusBarColor = event.statusBarColor,
                    )
                }
            }

            Event.SetUser -> {
                val userModel = sharedPreferencesStorage.get()
                with(userModel) {
                    state.update {
                        it.copy(
                            user = User(
                                name = name,
                                birthday = birthday,
                                theme = theme,
                                isFirst = isFirst
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class Event {
        data class ChangeColorScheme(val targetColorScheme: ColorScheme, val useDark: Boolean) :
            Event()

        data class SetTheme(val user: User?) : Event()
        data class ChangeStatusBarColor(val statusBarColor: Color) : Event()
        data object SetUser : Event()
    }
}