package ru.plumsoftware.coffeeapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.coffee.R
import ru.plumsoftware.coffeeapp.ui.components.cards.CoffeeOfTheDayCard
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = Padding.Items.mediumScreenPadding,
                            alignment = Alignment.End
                        )
                    ) {
                        Text(
                            text = "${stringResource(id = state.welcome)} ${state.name}",
                            style = MaterialTheme.typography.titleLarge.copy(color = getExtendedColors().welcomeTextColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            textAlign = TextAlign.Start
                        )
                    }

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
                    CoffeeOfTheDayCard(
                        coffee = state.coffeeOfTheDay,
                        onCoffeeClick = {
                            homeViewModel.onOutput(HomeViewModel.Output.SelectCoffee(value = it))
                            homeViewModel.onOutput(HomeViewModel.Output.NavigateTo(route = Screens.COFFEE_DRINK))
                        }
                    )
                }
            }

            for (i in state.coffeeMatrix.indices) {
                if (state.isAdult)
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
                                type = state.coffeeMatrix[i][0].type,
                                coffeeList = state.coffeeMatrix[i],
                                onLikeClick = {
                                    onEvent(HomeViewModel.Event.Like(coffee = it))
                                },
                                onCoffeeClick = {
                                    homeViewModel.onOutput(
                                        HomeViewModel.Output.SelectCoffee(
                                            value = it
                                        )
                                    )
                                    homeViewModel.onOutput(HomeViewModel.Output.NavigateTo(route = Screens.COFFEE_DRINK))
                                }
                            )
                        }
                    }
                else {
                    item {
                        if (state.coffeeMatrix[i][0].type != stringArrayResource(id = R.array.tag_list)[9])
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
                                    type = state.coffeeMatrix[i][0].type,
                                    coffeeList = state.coffeeMatrix[i],
                                    onLikeClick = {
                                        onEvent(HomeViewModel.Event.Like(coffee = it))
                                    },
                                    onCoffeeClick = {
                                        homeViewModel.onOutput(
                                            HomeViewModel.Output.SelectCoffee(
                                                value = it
                                            )
                                        )
                                        homeViewModel.onOutput(
                                            HomeViewModel.Output.NavigateTo(
                                                route = Screens.COFFEE_DRINK
                                            )
                                        )
                                    }
                                )
                            }
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
                age = 18,
                coffeeStorage = null,
                name = "Test",
                userDatabase = null,
                output = {},
                isAdsLoading = false
            )

            Home(
                onEvent = homeViewModel::onEvent,
                homeViewModel = homeViewModel
            )
        }
    }
}