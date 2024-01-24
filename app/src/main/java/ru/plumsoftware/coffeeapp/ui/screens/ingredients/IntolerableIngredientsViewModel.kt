package ru.plumsoftware.coffeeapp.ui.screens.ingredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Ingredient
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class IntolerableIngredientsViewModel(
    private val output: (Output) -> Unit,
    intolerableIngredients: List<Ingredient>,
    private val sharedPreferencesStorage: SharedPreferencesStorage?,
    private val userDatabase: UserDatabase?
) : ViewModel() {

    private var isFirst: Boolean = true
    private val list: MutableList<Ingredient> = mutableListOf()

    init {
        viewModelScope.launch {
            isFirst = userDatabase?.dao?.getUser()?.isFirst == 1
        }

    }

    val state = MutableStateFlow(
        IntolerableIngredientsState(
            list = intolerableIngredients.toMutableList(),
            firstSetup = isFirst,
            intolerableIngredients = list
        )
    )

    fun onOutput(o: Output) {
        output(o)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.AddIntolerableIngredient -> {
                state.update {
                    if (event.selected) {
                        list.add(event.ingredient)
                    } else {
                        list.remove(event.ingredient)
                    }
                    it.copy(
                        intolerableIngredients = list
                    )
                }
            }

            Event.SaveData -> {
                viewModelScope.launch {
                    state.value.intolerableIngredients.forEach {
                        userDatabase?.dao?.insert(
                            intolerableIngredients = ru.plumsoftware.data.models.IntolerableIngredients(
                                ingredientId = it.id
                            )
                        )
                    }
                }
                sharedPreferencesStorage?.set(isFirst = 0)
            }
        }
    }

    sealed class Output {
        data object Go : Output()
    }

    sealed class Event {
        data class AddIntolerableIngredient(val ingredient: Ingredient, val selected: Boolean) :
            Event()

        data object SaveData : Event()
    }
}