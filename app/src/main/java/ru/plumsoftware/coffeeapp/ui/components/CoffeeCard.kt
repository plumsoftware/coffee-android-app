package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.coffeeapp.ui.theme.getMediumWeight
import ru.plumsoftware.data.models.Coffee

@Composable
fun CoffeeCard(
    coffee: Coffee,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
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
                .padding(all = Padding.Items.mediumScreenPadding)
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
                    space = Padding.Items.extraSmallScreenPadding,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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

                Text(
                    text = coffee.name, style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.3f
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Start
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = Padding.Items.smallScreenPadding, alignment = Alignment.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    painter = if (coffee.isLiked == 1) painterResource(id = R.drawable.liked_drink) else painterResource(
                        id = R.drawable.liked
                    ),
                    contentDescription = stringResource(
                        id = R.string.coffee_like_content_description
                    ),
                    tint = if (coffee.isLiked == 1) getExtendedColors().likeColor else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(size = Size.Coffee.likeSize)
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = Color.Transparent)
                        .clickable(
                            onClick = { TODO() },
                            enabled = true,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 12.dp
                            )
                        ),
                )
            }
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
        history = "asdvefbrgnthmyj,uki.ukjyhtgfdsafwergthyjukyilo;ulkyjthgbfdvbrthyjuki",
        roastingLevel = "Средняя прожарка",
        tastes = "",
        cookingMethod = "",
        description = "adsafdgehrtyjukil",
        ageRating = "14+",
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
                    coffee = mockCoffeeModel,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                CoffeeCard(
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