package ru.plumsoftware.coffeeapp.ui.screens.settings

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.coffeeapp.utilities.calculateAge
import ru.plumsoftware.coffeeapp.utilities.dateToLong
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class SettingsViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage?,
    user: User,
    private val output: (Output) -> Unit
) : ViewModel() {

    val state = MutableStateFlow(
        SettingsState(
            name = "",
            age = "",
            birthday = "",
            useDark = user.theme
        )
    )

    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeBirthday -> {
                sharedPreferencesStorage!!.set(birthday = dateToLong(inputString = event.birthday)!!)
                state.update {
                    it.copy(
                        birthday = event.birthday,
                        age = calculateAge(
                            dob = dateToLong(inputString = event.birthday)!!
                        )
                    )
                }
            }

            is Event.ChangeName -> {
                sharedPreferencesStorage!!.set(name = event.name)
                state.update {
                    it.copy(
                        name = event.name,
                    )
                }
            }

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

    fun onOutput(o: Output) {
        output(o)
    }

    sealed class Output {
        data class NavigateTo(val route: String) : Output()
        data class ChangeTheme(val targetColorScheme: ColorScheme, val useDark: Boolean) : Output()
    }

    sealed class Event {
        data class ChangeName(val name: String) : Event()
        data class ChangeBirthday(val birthday: String) : Event()
        data class ChangeRadioButton(val targetColorScheme: ColorScheme, val useDark: Boolean) :
            Event()
    }
}