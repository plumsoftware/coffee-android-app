package ru.plumsoftware.coffeeapp.ui.screens

import android.icu.util.Output
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.database.UserDatabase

class MainViewModel : ViewModel(), KoinComponent {

    val targetColor by mutableStateOf(LightColors)
    val navColor = targetColor.background
    val statusBarColor by mutableStateOf(targetColor.background)
    private var isFirst by mutableStateOf(true)

    fun onOutput(output: Output) {
        when (output) {
            Output.OpenAppearance -> TODO()
        }
    }

    sealed class Output {
        object OpenAppearance : Output()
    }
}