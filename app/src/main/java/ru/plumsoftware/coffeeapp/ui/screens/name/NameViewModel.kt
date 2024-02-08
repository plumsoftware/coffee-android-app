package ru.plumsoftware.coffeeapp.ui.screens.name

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.coffeeapp.utilities.calculateAge
import ru.plumsoftware.coffeeapp.utilities.dateToLong
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class NameViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage?,
    private val output: (Output) -> Unit
) : ViewModel() {

    val state = MutableStateFlow(NameState())

    fun onOutput(o: Output) {
        output(o)
    }

    @SuppressLint("SimpleDateFormat")
    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeBirthday -> {
                state.update {
                    it.copy(
                        birthday = event.birthday,
                        isActive = state.value.name.isNotEmpty() && event.birthday.isNotEmpty(),
                        age = calculateAge(
                            dob = dateToLong(inputString = event.birthday)!!
                        )
                    )
                }
            }

            is Event.ChangeName -> {
                state.update {
                    it.copy(
                        name = event.name,
                        isActive = event.name.isNotEmpty() && state.value.birthday.isNotEmpty()
                    )
                }
            }

            is Event.SaveData -> {
                sharedPreferencesStorage!!.set(name = event.name)
                sharedPreferencesStorage.set(birthday = dateToLong(inputString = event.birthday)!!)
            }
        }
    }

    sealed class Event {
        data class ChangeName(val name: String) : Event()
        data class ChangeBirthday(val birthday: String) : Event()
        data class SaveData(val birthday: String, val name: String) : Event()
    }

    sealed class Output {
        data object Go : Output()
    }
}