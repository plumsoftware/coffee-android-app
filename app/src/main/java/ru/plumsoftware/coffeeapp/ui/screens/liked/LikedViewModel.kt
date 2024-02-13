package ru.plumsoftware.coffeeapp.ui.screens.liked

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.domain.storage.CoffeeStorage
import java.util.Locale

class LikedViewModel(
    coffeeStorage: CoffeeStorage?,
    private val userDatabase: UserDatabase?,
    private val output: (Output) -> Unit
) : ViewModel() {

    private val coffeeList: MutableList<Coffee> =
        coffeeStorage!!.getD().map { it as Coffee }.toMutableList()
    private var coffeeMatrix: List<List<Coffee>>

    private fun filterCoffeeList(tag: String): List<Coffee> {
        return if (tag.isNotEmpty()) coffeeList.filter { it.type == tag } else coffeeList
    }

    private fun combineFilterList(tag: String, coffeeName: String): List<Coffee> {
        val list1 = if (tag.isNotEmpty()) coffeeList.filter { it.type == tag } else coffeeList
        return if (coffeeName.isNotEmpty()) list1.filter {
            it.name.lowercase(Locale.getDefault())
                .contains(coffeeName.lowercase(Locale.getDefault()))
        } else list1
    }

    init {

        runBlocking {
            val likedDrinks = userDatabase!!.dao.get()
            likedDrinks.forEachIndexed { _, likedDrink ->
                coffeeList.forEachIndexed { index, coffee ->
                    if (likedDrink.drinkId == coffee.id)
                        coffeeList[index] = coffee.copy(isLiked = 1)
                }
            }

            coffeeList.removeIf { it.isLiked == 0 }
            coffeeMatrix = filterCoffeeList(tag = "").groupBy { it.type }.values.toList()
        }
    }

    val state = MutableStateFlow(LikedState(coffeeMatrix = coffeeMatrix, tag = ""))

    fun onEvent(event: Event) {
        when (event) {
            is Event.ChangeQuery -> {
                state.update {
                    it.copy(
                        query = event.value
                    )
                }
            }

            is Event.ChangeTag -> {
                val tag = if (event.index != 0) {
                    event.item
                } else {
                    ""
                }

                state.update {
                    it.copy(
                        tag = tag,
                        coffeeMatrix = filterCoffeeList(tag = tag).groupBy { it2 -> it2.type }.values.toList()
                    )
                }
            }

            Event.Search -> {
                state.update {
                    it.copy(
                        coffeeMatrix = combineFilterList(
                            tag = state.value.tag,
                            coffeeName = state.value.query
                        ).groupBy { l -> l.type }.values.toList()
                    )
                }
            }

            Event.ClearQuery -> {
                state.update {
                    it.copy(
                        query = ""
                    )
                }
            }
        }
    }

    fun onOutput(o: Output) {
        output(o)
    }

    sealed class Output {
        data class NavigateTo(val route: String) : Output()
        data class SelectCoffee(val value: Coffee) : Output()
    }

    sealed class Event {
        data class ChangeQuery(val value: String) : Event()
        data class ChangeTag(val index: Int, val item: String) : Event()
        data object Search : Event()
        data object ClearQuery : Event()
    }
}