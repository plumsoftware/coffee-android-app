package ru.plumsoftware.coffeeapp.ui.screens.coffee

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.IntolerableIngredients

class CoffeeViewModel(
    private val userDatabase: UserDatabase?,
    selectedCoffee: Coffee
) : ViewModel() {

    private val list: MutableList<IntolerableIngredients> = mutableListOf()

    init {
        runBlocking {
            val intolerableIngredients = userDatabase!!.dao.getIntolerableIngredients()
            intolerableIngredients?.forEachIndexed { _, intolerableIngredient ->
                list.forEachIndexed { index, coffee ->
                    list[index] = intolerableIngredient
                }
            }
        }
    }

    val state = MutableStateFlow(
        CoffeeState(
            intolerableIngredients = list,
            selectedCoffee = selectedCoffee
        )
    )
}