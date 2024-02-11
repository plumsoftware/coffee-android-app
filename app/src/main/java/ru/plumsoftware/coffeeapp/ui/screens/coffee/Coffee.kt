package ru.plumsoftware.coffeeapp.ui.screens.coffee

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.models.Coffee

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeScreen(coffee: Coffee) {

    val isLiked = remember {
        mutableIntStateOf(coffee.isLiked)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.Items.mediumScreenPadding,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = Size.Coffee.fullCoffeeImageHeight),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(id = coffee.imageResId),
                    contentDescription = stringResource(
                        id = R.string.coffee_image__content_description
                    )
                )

                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = .5f)
                    ),
                    title = {
                        Text(
                            text = stringResource(id = R.string.drink),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = stringResource(
                                    id = R.string.back_icon_content_description
                                )
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick =
                            {
                                if (isLiked.intValue == 1) isLiked.intValue =
                                    0 else isLiked.intValue = 1
                            }
                        ) {
                            Icon(
                                painter = if (isLiked.intValue == 1) painterResource(id = R.drawable.liked_drink) else painterResource(
                                    id = R.drawable.liked
                                ),
                                contentDescription = stringResource(
                                    id = R.string.coffee_like_content_description
                                ),
                                tint = if (isLiked.intValue == 1) getExtendedColors().likeColor else MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(size = Size.Coffee.likeSize)
                                    .clip(MaterialTheme.shapes.medium),
                            )
                        }
                    }
                )
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Screens.smallScreenPadding)
            ) {

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CoffeeScreenPreview() {

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

    CoffeeAppTheme {
        Surface {
            CoffeeScreen(coffee = mockCoffeeModel)
        }
    }
}