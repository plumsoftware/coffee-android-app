package ru.plumsoftware.coffeeapp.ui.screens.main

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.utilities.calculateAge
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

@Immutable
class MainViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val vibrator: Vibrator,
    private val output: (Output) -> Unit,
) : ViewModel() {

    private val user = User(
        name = sharedPreferencesStorage.get().name,
        birthday = sharedPreferencesStorage.get().birthday,
        theme = sharedPreferencesStorage.get().theme,
        isFirst = sharedPreferencesStorage.get().isFirst
    )

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
            user = user,
            age = calculateAge(user.birthday).toInt(),
            isAppOpenAdsLoading = false,
            isInterstitialAdsLoading = false
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
                state.value.selectedCoffeeList.add(event.value)
                state.update {
                    it.copy(
                        selectedCoffeeList = it.selectedCoffeeList
                    )
                }
            }

            is Event.ChangeNavBarColor -> {
                state.update {
                    it.copy(
                        navColor = event.navBarColor
                    )
                }
            }

            is Event.ChangeAppOpenLoadingState -> {
                state.update {
                    it.copy(
                        isAppOpenAdsLoading = event.value
                    )
                }
            }

            is Event.ChangeInterstitialLoadingState -> {
                state.update {
                    it.copy(
                        isInterstitialAdsLoading = event.value
                    )
                }
            }

            Event.RemoveLast -> {
                state.value.selectedCoffeeList.removeLast()
                state.update {
                    it.copy(
                        selectedCoffeeList = state.value.selectedCoffeeList
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
        data class ChangeNavBarColor(val navBarColor: Color) : Event()
        data class SelectCoffeeDrink(val value: Coffee) : Event()
        data object SetUser : Event()
        data object Vibrate : Event()
        data class ChangeAppOpenLoadingState(val value: Boolean) : Event()
        data class ChangeInterstitialLoadingState(val value: Boolean) : Event()
        data object RemoveLast: Event()
    }
}