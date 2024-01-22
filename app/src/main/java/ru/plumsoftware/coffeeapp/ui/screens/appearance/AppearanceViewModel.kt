package ru.plumsoftware.coffeeapp.ui.screens.appearance

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class AppearanceViewModel(
    private val userDatabase: UserDatabase?,
    useDark: Boolean,
    private val output: (Output) -> Unit
) : ViewModel() {

    val state = MutableStateFlow(
        AppearanceState(
            useDark = useDark,
            selected1 = !useDark,
            selected2 = useDark
        )
    )

    fun onOutput(o: Output) {
        output(o)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeRadioButton1 -> {
                state.update {
                    it.copy(
                        selected1 = event.selected,
                        selected2 = false,
                    )
                }
                onLabel(Label.ChangeThemeToLight)
            }

            is Event.ChangeRadioButton2 -> {
                state.update {
                    it.copy(
                        selected1 = false,
                        selected2 = event.selected,
                    )
                }
                onLabel(Label.ChangeThemeToDark)
            }
        }
    }

    fun onLabel(label: Label) {
        when (label) {
            Label.ChangeThemeToLight -> {
                viewModelScope.launch {
                    onOutput(
                        Output.ChangeTheme(
                            useDark = false,
                            targetColorScheme = LightColors
                        )
                    )
                    userDatabase!!.dao.upsert(
                        user = User(
                            theme = false
                        )
                    )
                }
            }

            Label.ChangeThemeToDark -> {
                viewModelScope.launch {
                    onOutput(
                        Output.ChangeTheme(
                            useDark = true,
                            targetColorScheme = DarkColors
                        )
                    )
                    userDatabase!!.dao.upsert(
                        user = User(
                            theme = true
                        )
                    )
                }
            }

            is Label.SetupTheme -> {
                viewModelScope.launch {
                    state.update {
                        val useDark = userDatabase!!.dao.getUser()!!.theme
                        it.copy(
                            useDark = useDark,
                            selected1 = !useDark,
                            selected2 = useDark
                        )
                    }
                }
            }
        }
    }

    sealed class Label {
        data object ChangeThemeToLight : Label()
        data object ChangeThemeToDark : Label()
        data object SetupTheme : Label()
    }

    sealed class Output {
        data class ChangeTheme(val targetColorScheme: ColorScheme, val useDark: Boolean) : Output()
        data object Go : Output()
    }

    sealed class Event {
        data class ChangeRadioButton1(val useDark: Boolean, val selected: Boolean) : Event()
        data class ChangeRadioButton2(val useDark: Boolean, val selected: Boolean) : Event()
    }
}