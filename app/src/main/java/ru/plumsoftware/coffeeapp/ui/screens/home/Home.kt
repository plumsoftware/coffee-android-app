package ru.plumsoftware.coffeeapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.cards.CoffeeOfTheDayCard
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.models.Coffee

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(homeViewModel: HomeViewModel, onEvent: (HomeViewModel.Event) -> Unit) {

    val state = homeViewModel.state.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                onClick = {
                    homeViewModel.onOutput(HomeViewModel.Output.NavigateTo(route = it))
                }
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = Padding.Items.largeScreenPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = getExtendedColors().welcomeBackgroundColor)
                        .padding(all = Padding.Screens.smallScreenPadding)
                ) {
                    Text(
                        text = "${stringResource(id = state.welcome)} ${state.name}",
                        style = MaterialTheme.typography.titleLarge.copy(color = getExtendedColors().welcomeTextColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Start
                    )

                    SearchField(
                        onFocusChange = {
                            homeViewModel.onOutput(HomeViewModel.Output.NavigateTo(route = Screens.SEARCH))
                        }
                    )
                }
            }

            item {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(all = Padding.Screens.smallScreenPadding)
                ) {
                    CoffeeOfTheDayCard(coffee = state.coffeeOfTheDay)
                }
            }

            for (i in state.coffeeMatrix.indices) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                horizontal = Padding.Screens.smallScreenPadding,
                                vertical = Padding.Screens.extraSmallScreenPadding
                            )
                    ) {
                        HorizontalCoffeeList(
                            type = state.coffeeMatrix[i][i].type,
                            coffeeList = state.coffeeMatrix[i],
                            onLikeClick = {
                                onEvent(HomeViewModel.Event.Like(coffee = it))
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(height = Size.Divider.homeHeight))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePreview() {

    val mockCoffeeModel = Coffee(
        id = -1,
        name = "Капучино",
        imageResId = R.drawable.mock_coffee_drink,
        isLiked = 1,
        type = "Капучино",
        roastingLevel = "Средняя прожарка",
        description = "adsafdgehrtyjukil",
        ageRating = 14,
        ingredients = emptyList()
    )

    val mockList = listOf<Coffee>(
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
        mockCoffeeModel,
    )

    val mockCoffeeMatrix = listOf<List<Coffee>>(
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
        mockList,
    )

    CoffeeAppTheme(useDarkTheme = false) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {

            val systemUiController = rememberSystemUiController()
            val navigationBarColor = MaterialTheme.colorScheme.background
            val statusBarColor = getExtendedColors().welcomeBackgroundColor

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = statusBarColor,
                )
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor
                )
            }

            val homeViewModel = HomeViewModel(
                coffeeStorage = null,
                name = "Test",
                output = {},
                userDatabase = null
            )

            Home(
                onEvent = homeViewModel::onEvent,
                homeViewModel = homeViewModel
            )
        }
    }
}