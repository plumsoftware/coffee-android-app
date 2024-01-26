package ru.plumsoftware.coffeeapp.ui.screens.search

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.data.models.Coffee
import java.util.Locale
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

    private fun combineFilterList(tag: String, coffeeName: String): List<Coffee> {
        val list1 = if (tag.isNotEmpty()) coffeeList.filter { it.type == tag } else coffeeList
        return if (coffeeName.isNotEmpty()) list1.filter {
            it.name.lowercase(Locale.getDefault())
                .contains(coffeeName.lowercase(Locale.getDefault()))
        } else list1
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
        }
    }

    sealed class Output {

    }

    sealed class Event {
        data class ChangeQuery(val value: String) : Event()
        data class ChangeTag(val index: Int, val item: String) : Event()
        data object Search : Event()
        data object ChangeFocus : Event()
        data object ClearQuery : Event()
    }
}