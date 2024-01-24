package ru.plumsoftware.coffeeapp.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.plumsoftware.data.models.Coffee

class HomeViewModel(
    private val coffeeList: List<Coffee>,
    private val output: (Output) -> Unit
) :
    ViewModel() {
    val state = MutableStateFlow(HomeState(coffeeList = coffeeList))

    fun onOutput(o: Output) {
        output(o)
    }

    sealed class Output {

    }

    sealed class Event {

    }
}