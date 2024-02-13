package ru.plumsoftware.coffeeapp.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.data.models.Coffee

@Composable
fun CoffeeOfTheDayCard(coffee: Coffee, onCoffeeClick: (Coffee) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Padding.Items.mediumScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = stringResource(id = R.string.coffee_of_the_day),
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Start
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = Size.Elevation.coffeeOfTheDayCardElevation
            ),
            onClick = {
                onCoffeeClick(coffee)
            }
        ) {
            Box(
                modifier = Modifier
                    .height(height = Size.Coffee.coffeeOfTheDayImageHeight)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Image(
                    painter = painterResource(id = coffee.imageResId),
                    contentDescription = stringResource(
                        id = R.string.coffee_of_the_day_content_description
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .align(alignment = Alignment.Center)
                        .clip(shape = MaterialTheme.shapes.small)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            shape = MaterialTheme.shapes.small,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    Color.Transparent
                                )
                            )
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = Padding.Items.mediumScreenPadding,
                            alignment = Alignment.Bottom
                        ),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(all = Padding.Items.largeScreenPadding)
                    ) {
                        Text(
                            text = coffee.name,
                            style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onBackground)
                        )
                        Text(
                            text = stringResource(id = R.string.taste),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CoffeeOfTheDayCardPreview() {

    val mockCoffeeModel = Coffee(
        id = -1,
        name = "Капучино",
        imageResId = R.drawable.mock_coffee_drink,
        isLiked = 1,
        roastingLevel = "Средняя прожарка",
        type = "Капучино",
        description = "adsafdgehrtyjukil",
        ageRating = 14,
        ingredients = emptyList()
    )

    CoffeeAppTheme(useDarkTheme = false) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            CoffeeOfTheDayCard(
                coffee = mockCoffeeModel,
                onCoffeeClick = {}
            )
        }
    }
}