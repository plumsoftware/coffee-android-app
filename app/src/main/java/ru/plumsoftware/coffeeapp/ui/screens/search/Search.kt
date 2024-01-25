package ru.plumsoftware.coffeeapp.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.components.lists.TagList
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(searchViewModel: SearchViewModel, onEvent: (SearchViewModel.Event) -> Unit) {

    val state = searchViewModel.state.collectAsState().value

    Scaffold(
        bottomBar = {
            BottomNavBar(
                onClick = {

                }
            )
        }
    ) {
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
                SearchField()
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
                        coffeeList = state.coffeeMatrix[i]
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(height = Size.Divider.homeHeight))
            }
        }
    }
}