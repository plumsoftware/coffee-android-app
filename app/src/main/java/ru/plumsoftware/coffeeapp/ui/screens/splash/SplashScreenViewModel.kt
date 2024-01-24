package ru.plumsoftware.coffeeapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class SplashScreenViewModel(
    private val output: (Output) -> Unit,
    private val sharedPreferencesStorage: SharedPreferencesStorage?
) : ViewModel(), KoinComponent {

    fun getIsFirst(): Boolean = sharedPreferencesStorage!!.get().isFirst == 1

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class GetUser(val isFirst: Boolean) : Output()
    }
}