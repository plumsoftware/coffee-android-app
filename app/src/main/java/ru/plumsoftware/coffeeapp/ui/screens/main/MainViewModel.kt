package ru.plumsoftware.coffeeapp.ui.screens.main

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class MainViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val vibrator: Vibrator,
    private val output: (Output) -> Unit,
) : ViewModel() {

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect: VibrationEffect =
                VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.cancel()
            vibrator.vibrate(vibrationEffect)
        } else {
            return
        }
    }

    val state = MutableStateFlow(
        MainState(
            name = sharedPreferencesStorage.get().name,
            user = User(
                name = sharedPreferencesStorage.get().name,
                birthday = sharedPreferencesStorage.get().birthday,
                theme = sharedPreferencesStorage.get().theme,
                isFirst = sharedPreferencesStorage.get().isFirst,
            )
        )
    )

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
                            name = sharedPreferencesStorage.get().name,
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

            Event.Vibrate -> {
                vibrate()
            }

            is Event.SelectCoffeeDrink -> {
                state.update {
                    it.copy(
                        selectedCoffee = event.value
                    )
                }
            }
        }
    }

    fun onOutput(o: Output) {
        output(o)
    }

    sealed class Output {
        data class NavigateTo(val route: String) : Output()
    }

    sealed class Event {
        data class ChangeColorScheme(val targetColorScheme: ColorScheme, val useDark: Boolean) :
            Event()

        data class SetTheme(val user: User?) : Event()
        data class ChangeStatusBarColor(val statusBarColor: Color) : Event()
        data class SelectCoffeeDrink(val value: Coffee) : Event()
        data object SetUser : Event()
        data object Vibrate : Event()
    }
}