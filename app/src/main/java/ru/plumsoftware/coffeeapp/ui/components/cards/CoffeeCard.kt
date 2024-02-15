package ru.plumsoftware.coffeeapp.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.buttons.LikeButton
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.coffeeapp.ui.theme.getMediumWeight
import ru.plumsoftware.data.models.Coffee

@Composable
fun CoffeeCard(
    coffee: Coffee,
    modifier: Modifier = Modifier,
    onLikeClick: (Coffee) -> Unit = {},
    onCoffeeClick: (Coffee) -> Unit
) {

    Button(
        onClick = {
            onCoffeeClick(coffee)
        },
        modifier = modifier,
        contentPadding = PaddingValues(all = Padding.Items.zero),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = getExtendedColors().cardBackground,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = Padding.Items.mediumScreenPadding,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Card(
                modifier = Modifier.wrapContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = getExtendedColors().cardBackground,
                ),
                shape = MaterialTheme.shapes.small,
                ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Size.Coffee.coffeePreviewImageHeight),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = coffee.imageResId),
                    contentDescription = stringResource(
                        id = R.string.coffee_image__content_description
                    ),
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = Padding.Items.zero,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = Padding.Items.extraSmallScreenPadding,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = Padding.Items.mediumScreenPadding)
                ) {
                    Text(
                        text = coffee.name, style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = getMediumWeight(),
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Start
                    )

                    val stringBuilder: StringBuilder = java.lang.StringBuilder("")
                    coffee.ingredients.reversed().forEachIndexed() { index, ingredient ->
                        stringBuilder
                            .append(ingredient.name)
                            .append(" • ")
                    }

                    Text(
                        text = stringBuilder.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.3f
                            )
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Start,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                LikeButton(isCoffeeLiked = coffee.isLiked) {
                    onLikeClick(coffee)
                }
            }

//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(
//                    space = Padding.Items.smallScreenPadding, alignment = Alignment.Start
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .padding(
//                        start = Padding.Items.mediumScreenPadding,
//                        end = Padding.Items.mediumScreenPadding,
//                        bottom = Padding.Items.mediumScreenPadding
//                    )
//            ) {
//
//            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CoffeeCardPreview() {

    val mockCoffeeModel = Coffee(
        id = -1,
        name = "Капучино",
        imageResId = R.drawable.mock_coffee_drink,
        isLiked = 1,
        roastingLevel = "Средняя прожарка",
        description = "adsafdgehrtyjukil",
        ageRating = 12,
        type = "Капучино",
        ingredients = emptyList()
    )

    CoffeeAppTheme(useDarkTheme = true) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = Padding.Items.mediumScreenPadding, alignment = Alignment.Start
                )
            ) {
                CoffeeCard(
                    onCoffeeClick = {},
                    coffee = mockCoffeeModel,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                CoffeeCard(
                    onCoffeeClick = {},
                    coffee = mockCoffeeModel,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}