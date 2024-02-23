package ru.plumsoftware.coffeeapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.LikedDrink
import ru.plumsoftware.domain.storage.CoffeeStorage
import java.util.Calendar

class HomeViewModel(
    age: Int,
    coffeeStorage: CoffeeStorage?,
    name: String,
    private val userDatabase: UserDatabase?,
    private val output: (Output) -> Unit
) : ViewModel() {

    private val list: MutableList<Coffee> = coffeeStorage!!.getD().map { it as Coffee }.toMutableList()

    init {
        runBlocking {
            val likedDrinks = userDatabase!!.dao.get()
            likedDrinks.forEachIndexed { _, likedDrink ->
                list.forEachIndexed { index, coffee ->
                    if (likedDrink.drinkId == coffee.id)
                        list[index] = coffee.copy(isLiked = 1)
                }
            }
        }
    }

    private val coffeeMatrix = list.groupBy { it.type }.values.toList()

    private val coffeeOfTheDay = coffeeStorage!!.getR() as Coffee

    private fun welcome(): Int {
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return when {
            currentTime < 12 -> {
                R.string.welcome_morning
            }

            currentTime < 18 -> {
                R.string.welcome_day
            }

            else -> {
                R.string.welcome_evening
            }
        }
    }

    val state =
        MutableStateFlow(
            HomeState(
                coffeeMatrix = coffeeMatrix,
                coffeeOfTheDay = coffeeOfTheDay,
                name = name,
                welcome = welcome(),
                isAdult = age >= App.INSTANCE.getString(R.string.adult_age).toInt()
            )
        )

    fun onOutput(o: Output) {
        output(o)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.Like -> {
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

    sealed class Output {
        data class NavigateTo(val route: String) : Output()
        data class SelectCoffee(val value: Coffee) : Output()
    }

    sealed class Event {
        data class Like(val coffee: Coffee) : Event()
    }
}