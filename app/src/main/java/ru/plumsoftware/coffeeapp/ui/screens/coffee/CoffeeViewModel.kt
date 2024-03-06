package ru.plumsoftware.coffeeapp.ui.screens.coffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.IntolerableIngredients
import ru.plumsoftware.data.models.LikedDrink
import ru.plumsoftware.domain.storage.CoffeeStorage

class CoffeeViewModel(
    private val userDatabase: UserDatabase?,
    private val coffeeStorage: CoffeeStorage?,
    private val output: (Output) -> Unit,
    selectedCoffee: Coffee,
    age: Int,
    isInterstitialLoading: Boolean
) : ViewModel() {

    private val list: MutableList<IntolerableIngredients> = mutableListOf()
    private val randomList: MutableList<Coffee> = mutableListOf()

    init {
        runBlocking {
            val intolerableIngredients = userDatabase!!.dao.getIntolerableIngredients()
            intolerableIngredients?.forEachIndexed { _, intolerableIngredient ->
                list.add(intolerableIngredient)
            }
        }

        runBlocking {
            for (i in coffeeStorage?.getD()!!.indices) {
                val randomDrink = coffeeStorage.getR() as Coffee
                if (!randomList.contains(randomDrink)) {
                    randomList.add(randomDrink)
                }
            }
        }

        runBlocking {
            val likedDrinks: List<LikedDrink>? = userDatabase?.dao?.get()
            likedDrinks!!.forEachIndexed { _, likedDrink ->
                randomList.forEachIndexed { index, coffee ->
                    if (likedDrink.drinkId == coffee.id)
                        randomList[index] = coffee.copy(isLiked = 1)
                }
            }
        }
    }

    val state = MutableStateFlow(
        CoffeeState(
            intolerableIngredients = list,
            selectedCoffee = selectedCoffee,
            randomCoffeeDrinks = randomList,
            age = age,
            isInterstitialLoading = isInterstitialLoading
        )
    )

    fun onOutput(o: Output) {
        output(o)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.AddToLiked -> {
                viewModelScope.launch {
                    with(event.coffee) {
                        if (isLiked == 1)
                            userDatabase!!.dao.deleteById(drinkId = id)
                        else {
                            userDatabase!!.dao.upsert(
                                LikedDrink(
                                    drinkId = event.coffee.id
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class Event {
        data class AddToLiked(val coffee: Coffee) : Event()
    }

    sealed class Output {
        data class SelectCoffee(val value: Coffee) : Output()
        data class NavigateTo(val route: String) : Output()

        data object PopBackStack : Output()
    }
}