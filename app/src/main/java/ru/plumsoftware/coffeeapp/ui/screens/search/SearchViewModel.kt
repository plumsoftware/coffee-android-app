package ru.plumsoftware.coffeeapp.ui.screens.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.coffee.R as C

class SearchViewModel(
    private val coffeeList: List<Coffee>,
    tag: String,
    private val output: (Output) -> Unit
) : ViewModel() {

    private var coffeeMatrix: List<List<Coffee>>

    private fun filterCoffeeList(tag: String): List<Coffee> {
        return if (tag.isNotEmpty()) coffeeList.filter { it.type == tag } else coffeeList
    }

    init {
        coffeeMatrix = filterCoffeeList(tag = tag).groupBy { it.type }.values.toList()
    }

    val state = MutableStateFlow(
        SearchState(
            coffeeMatrix = coffeeMatrix,
            tagArray = C.array.tag_list,
            tag = tag
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
                        coffeeMatrix = filterCoffeeList(tag = tag).groupBy { it.type }.values.toList()
                    )
                }
            }
        }
    }

    sealed class Output {

    }

    sealed class Event {
        data class ChangeQuery(val value: String) : Event()
        data class ChangeTag(val index: Int, val item: String) : Event()
    }
}