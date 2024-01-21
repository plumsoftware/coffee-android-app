package ru.plumsoftware.coffeeapp.ui.screens.main

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class MainViewModel(
    private val userDatabase: UserDatabase
) : ViewModel(), KoinComponent {

    val state = MutableStateFlow(MainState())

    fun onEvent(event: Event) {
        when (event) {
            is Event.SetUser -> {
                viewModelScope.launch {
                    if (event.user == null) {
                        userDatabase.dao.upsert(user = User())
                    } else {
                        state.update {
                            it.copy(
                                user = event.user,
                                useDark = event.user.theme,
                                targetColorScheme = if (event.user.theme) DarkColors else LightColors,
                                navColor = if (event.user.theme) DarkColors.background else LightColors.background,
                                statusBarColor = if (event.user.theme) DarkColors.background else LightColors.background
                            )
                        }
                    }
                }
            }

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
                viewModelScope.launch {
                    state.update {
                        it.copy(
                            useDark = event.user!!.theme,
                            targetColorScheme = if (event.user.theme) DarkColors else LightColors,
                            navColor = if (event.user.theme) DarkColors.background else LightColors.background,
                            statusBarColor = if (event.user.theme) DarkColors.background else LightColors.background
                        )
                    }
                }
            }
        }
    }

    sealed class Event {
        data class SetUser(val user: User?) : Event()
        data class ChangeColorScheme(val targetColorScheme: ColorScheme, val useDark: Boolean) :
            Event()

        data class SetTheme(val user: User?) : Event()
    }
}