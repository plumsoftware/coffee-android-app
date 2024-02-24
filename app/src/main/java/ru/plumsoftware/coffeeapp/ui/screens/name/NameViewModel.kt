package ru.plumsoftware.coffeeapp.ui.screens.name

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.errorprone.annotations.Immutable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.plumsoftware.coffeeapp.utilities.calculateAge
import ru.plumsoftware.coffeeapp.utilities.dateToLong
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

@Suppress("OPT_IN_USAGE_FUTURE_ERROR")
class NameViewModel(
    private val sharedPreferencesStorage: SharedPreferencesStorage?,
    private val output: (Output) -> Unit
) : ViewModel() {

    val state = MutableStateFlow(NameState())
    val label = MutableSharedFlow<Label>()

    fun onOutput(o: Output) {
        output(o)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("SimpleDateFormat")
    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeBirthday -> {
                state.update {
                    it.copy(
                        birthday = event.birthday,
                        isActive = state.value.name.isNotEmpty() && event.birthday.isNotEmpty() && state.value.checkBox && calculateAge(
                            dob = dateToLong(inputString = event.birthday)!!
                        ).toInt() > 14,
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
                        isActive = event.name.isNotEmpty() && state.value.birthday.isNotEmpty() && state.value.checkBox && calculateAge(
                            dob = dateToLong(inputString = state.value.birthday)!!
                        ).toInt() > 14
                    )
                }
            }

            is Event.SaveData -> {
                sharedPreferencesStorage!!.set(name = event.name)
                sharedPreferencesStorage.set(birthday = dateToLong(inputString = event.birthday)!!)
            }

            is Event.TogglePrivacyPolicy -> {
                state.update {
                    it.copy(
                        checkBox = event.checked,
                        isActive = state.value.name.isNotEmpty() && state.value.birthday.isNotEmpty() && event.checked && calculateAge(
                            dob = dateToLong(inputString = state.value.birthday)!!
                        ).toInt() > 14,
                    )
                }

                if (event.checked) {
                    sharedPreferencesStorage?.setAgreeDate(agreeDate = System.currentTimeMillis())
                }
            }

            Event.ShowBottomSheetDialog -> {
                state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }

                viewModelScope.launch {
                    label.emit(Label.ShowBottomSheetDialog)
                }
            }

            Event.HideBottomSheetDialog -> {
                state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }

                viewModelScope.launch {
                    label.emit(Label.HideBottomSheetDialog)
                }
            }
        }
    }

    @Immutable
    sealed class Label {
        data object ShowBottomSheetDialog : Label()
        data object HideBottomSheetDialog : Label()
    }

    @Immutable
    sealed class Event {
        data class ChangeName(val name: String) : Event()
        data class ChangeBirthday(val birthday: String) : Event()
        data class SaveData(val birthday: String, val name: String) : Event()
        data class TogglePrivacyPolicy(val checked: Boolean) : Event()
        data object ShowBottomSheetDialog : Event()
        data object HideBottomSheetDialog : Event()
    }

    sealed class Output {
        data object Go : Output()
    }
}