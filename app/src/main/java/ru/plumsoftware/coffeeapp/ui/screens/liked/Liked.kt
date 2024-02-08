package ru.plumsoftware.coffeeapp.ui.screens.liked

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffee.R as C
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.components.lists.TagList
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Liked(
    likedViewModel: LikedViewModel,
    onEvent: (LikedViewModel.Event) -> Unit,
    onOutput: (LikedViewModel.Output) -> Unit
) {

    val state = likedViewModel.state.collectAsState().value

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = 1,
                onClick = {
                    onOutput(LikedViewModel.Output.NavigateTo(route = it))
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
                SearchField(
                    query = state.query,
                    onValueChange = {
                        onEvent(LikedViewModel.Event.ChangeQuery(value = it))
                    },
                    onClick = {
                        onEvent(LikedViewModel.Event.Search)
                    },
                    clearQuery = {
                        onEvent(LikedViewModel.Event.ClearQuery)
                    }
                )
            }

            item {
                TagList(
                    tagArray = stringArrayResource(id = C.array.tag_list),
                    onClick = { index, item ->
                        onEvent(LikedViewModel.Event.ChangeTag(index = index, item = item))
                    }
                )
            }

            for (i in state.coffeeMatrix.indices) {
                item {
                    HorizontalCoffeeList(
                        type = state.coffeeMatrix[i][0].type,
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LikedPreview() {
    CoffeeAppTheme {
        Surface {

            val viewModel = LikedViewModel(
                coffeeStorage = null,
                userDatabase = null,
                output = {}
            )
            Liked(likedViewModel = viewModel, viewModel::onEvent, viewModel::onOutput)
        }
    }
}