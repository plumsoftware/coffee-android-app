package ru.plumsoftware.coffeeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.cards.CoffeeOfTheDayCard
import ru.plumsoftware.coffeeapp.ui.components.fill_in.SearchField
import ru.plumsoftware.coffeeapp.ui.components.lists.HorizontalCoffeeList
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.models.Coffee

@Composable
fun Home(coffeeOfTheDay: Coffee, coffeeMatrix: List<List<Coffee>>, name: String) {
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
                    text = "${stringResource(id = R.string.welcome)} $name",
                    style = MaterialTheme.typography.titleLarge.copy(color = getExtendedColors().welcomeTextColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Start
                )

                SearchField()
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
                CoffeeOfTheDayCard(coffee = coffeeOfTheDay)
            }
        }

        for (i in coffeeMatrix.indices) {
            item {
                HorizontalCoffeeList(type = coffeeMatrix[i][i].type, coffeeList = coffeeMatrix[i])
            }
        }

        item {
            Spacer(modifier = Modifier.height(height = Size.Divider.homeHeight))
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

            Home(
                coffeeOfTheDay = mockCoffeeModel,
                coffeeMatrix = mockCoffeeMatrix,
                name = "null"
            )
        }
    }
}