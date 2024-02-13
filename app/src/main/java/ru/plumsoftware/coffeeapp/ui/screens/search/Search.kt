package ru.plumsoftware.coffeeapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import kotlinx.coroutines.delay
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.components.lists.TagList
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(searchViewModel: SearchViewModel, onEvent: (SearchViewModel.Event) -> Unit) {

    val state = searchViewModel.state.collectAsState().value
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit, block = {
        onEvent(SearchViewModel.Event.ChangeFocus)
        delay(100)
        keyboard?.show()
    })

    Surface(color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                space = Padding.Items.mediumScreenPadding,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = Padding.Screens.smallScreenPadding)
        ) {
            item {
                SearchField(
                    query = state.query,
                    onValueChange = {
                        onEvent(SearchViewModel.Event.ChangeQuery(value = it))
                    },
                    onClick = {
                        onEvent(SearchViewModel.Event.Search)
                    },
                    clearQuery = {
                        onEvent(SearchViewModel.Event.ClearQuery)
                    },
                    focusRequester = state.focusRequester
                )
            }

            item {
                TagList(
                    tagArray = stringArrayResource(id = state.tagArray),
                    onClick = { index, item ->
                        onEvent(SearchViewModel.Event.ChangeTag(index = index, item = item))
                    }
                )
            }

            for (i in state.coffeeMatrix.indices) {
                item {
                    HorizontalCoffeeList(
                        type = state.coffeeMatrix[i][i].type,
                        coffeeList = state.coffeeMatrix[i],
                        onCoffeeClick = {
                            searchViewModel.onOutput(SearchViewModel.Output.SelectCoffee(value = it))
                            searchViewModel.onOutput(SearchViewModel.Output.NavigateTo(route = Screens.COFFEE_DRINK))
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(height = Size.Divider.homeHeight))
            }
        }
    }
}