package ru.plumsoftware.coffeeapp.ui.screens.appearance

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class AppearanceViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage?,
    private val output: (Output) -> Unit
) : ViewModel() {

    val state = MutableStateFlow(
        AppearanceState(
            useDark = sharedPreferencesStorage!!.get().theme
        )
    )

    fun onOutput(o: Output) {
        output(o)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeRadioButton -> {
                state.update {
                    it.copy(
                        useDark = event.useDark
                    )
                }
                sharedPreferencesStorage!!.set(theme = event.useDark)
            }
        }
    }

    sealed class Output {
        data class ChangeTheme(val targetColorScheme: ColorScheme, val useDark: Boolean) : Output()
        data object Go : Output()
    }

    sealed class Event {
        data class ChangeRadioButton(val useDark: Boolean) : Event()
    }
}