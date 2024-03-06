package ru.plumsoftware.coffeeapp.ui.screens.search

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.LikedDrink
import ru.plumsoftware.domain.storage.CoffeeStorage
import java.util.Locale
import ru.plumsoftware.coffee.R as C

@Immutable
class SearchViewModel(
    age: Int,
    private val userDatabase: UserDatabase?,
    coffeeStorage: CoffeeStorage?,
    tag: String,
    private val output: (Output) -> Unit
) : ViewModel() {

    private var coffeeMatrix: List<List<Coffee>>
    private val coffeeList: MutableList<Coffee> = coffeeStorage!!.getD().map { it as Coffee }.toMutableList()

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
            coffeeMatrix = filterCoffeeList(tag = tag).groupBy { it.type }.values.toList()
        }
    }

    val state = MutableStateFlow(
        SearchState(
            coffeeMatrix = coffeeMatrix,
            tagArray = C.array.tag_list,
            tag = tag,
            isAdult = age >= App.INSTANCE.getString(R.string.adult_age).toInt()
        )
    )

    fun onOutput(o: Output) {
        output(o)
    }

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

            Event.ChangeFocus -> {
                state.value.focusRequester.requestFocus()
            }

            Event.ClearQuery -> {
                state.update {
                    it.copy(
                        query = ""
                    )
                }
            }

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
        data class SelectCoffee(val value: Coffee) : Output()
        data class NavigateTo(val route: String) : Output()
    }

    sealed class Event {
        data class ChangeQuery(val value: String) : Event()
        data class ChangeTag(val index: Int, val item: String) : Event()
        data object Search : Event()
        data object ChangeFocus : Event()
        data object ClearQuery : Event()
        data class Like(val coffee: Coffee) : Event()
    }
}