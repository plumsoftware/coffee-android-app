package ru.plumsoftware.coffeeapp.ui.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.cards.CoffeeCard
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.data.models.Coffee

@Composable
fun HorizontalCoffeeList(type: String, coffeeList: List<Coffee>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.Items.mediumScreenPadding,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = Padding.Items.largeScreenPadding,
                vertical = Padding.Items.mediumScreenPadding
            )
    ) {

        Text(
            text = type,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        LazyRow {
            itemsIndexed(coffeeList) { index, item ->
                Box(modifier = Modifier.wrapContentSize()) {
                    CoffeeCard(
                        coffee = item,
                        modifier = Modifier
                            .width(width = Size.Coffee.coffeeCardWidth)
                    )
                }
                Spacer(modifier = Modifier.width(width = Padding.Items.mediumScreenPadding))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HorizontalCoffeeListPreview() {

    val mockCoffeeModel = Coffee(
        id = -1,
        name = "Капучино",
        imageResId = R.drawable.mock_coffee_drink,
        isLiked = 1,
        roastingLevel = "Средняя прожарка",
        description = "adsafdgehrtyjukil",
        ageRating = 14,
        type = "Капучино",
        ingredients = emptyList()
    )

    CoffeeAppTheme(useDarkTheme = false) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            HorizontalCoffeeList(
                type = "Капучино", coffeeList = listOf(
                    mockCoffeeModel,
                    mockCoffeeModel,
                    mockCoffeeModel,
                    mockCoffeeModel,
                    mockCoffeeModel,
                    mockCoffeeModel,
                    mockCoffeeModel
                )
            )
        }
    }
}