package ru.plumsoftware.coffeeapp.ui.screens.coffee

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.cards.CoffeeCard
import ru.plumsoftware.coffee.R as C
import ru.plumsoftware.coffeeapp.ui.components.cards.Tag
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.Ingredient

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeScreen(
    coffeeViewModel: CoffeeViewModel,
    output: (CoffeeViewModel.Output) -> Unit,
    event: (CoffeeViewModel.Event) -> Unit
) {

    val state = coffeeViewModel.state.collectAsState().value

    val isLiked = remember {
        mutableIntStateOf(state.selectedCoffee.isLiked)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {

        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = Padding.Items.smallScreenPadding,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = Size.Coffee.fullCoffeeImageHeight),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = state.selectedCoffee.imageResId),
                        contentDescription = stringResource(
                            id = R.string.coffee_image__content_description
                        )
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Padding.Screens.smallScreenPadding),
                        verticalArrangement = Arrangement.spacedBy(
                            space = Padding.Items.smallScreenPadding,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = Padding.Items.smallScreenPadding,
                                    alignment = Alignment.Top
                                ), horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .weight(.5f)
                                    .wrapContentHeight()
                            ) {
                                Text(
                                    text = state.selectedCoffee.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text =
                                    state.selectedCoffee.ingredients.size.toString() + " " + if (state.selectedCoffee.ingredients.size < 5) stringResource(
                                        id = R.string.ingredients_2
                                    ) else stringResource(
                                        id = R.string.ingredients_1
                                    ),
                                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                                )
                            }

                            Tag(
                                imageResId = C.drawable.coffee_beans,
                                title = state.selectedCoffee.roastingLevel,
                                isIntolerable = false
                            )
                        }

                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(
                                space = Padding.Items.smallScreenPadding,
                                alignment = Alignment.Top
                            ),
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            state.selectedCoffee.ingredients.forEach { ingrediient ->
//                        if (
//                            ingrediient.id == 1 ||
//                            ingrediient.id == 11 ||
//                            ingrediient.id == 19 ||
//                            ingrediient.id == 22 ||
//                            ingrediient.id == 23 ||
//                            ingrediient.id == 25 ||
//                            ingrediient.id == 26 ||
//                            ingrediient.id == 27 ||
//                            ingrediient.id == 28 ||
//                            ingrediient.id == 29 ||
//                            ingrediient.id == 30 ||
//                            ingrediient.id == 32 ||
//                            ingrediient.id == 38 ||
//                            ingrediient.id == 41 ||
//                            ingrediient.id == 42 ||
//                            ingrediient.id == 48 ||
//                            ingrediient.id == 51
//                            )
                                Tag(
                                    imageResId = ingrediient.iconId,
                                    title = ingrediient.name,
                                    isIntolerable = ingrediient.id == 1
                                )
                                Spacer(modifier = Modifier.width(width = Padding.Items.smallScreenPadding))
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = Padding.Screens.smallScreenPadding,
                                end = Padding.Screens.smallScreenPadding,
                                top = Padding.Screens.smallScreenPadding
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = Padding.Items.smallScreenPadding,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.how_to_cook),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = state.selectedCoffee.description,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = Padding.Screens.smallScreenPadding,
                                end = Padding.Screens.smallScreenPadding,
                                top = Padding.Screens.smallScreenPadding
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = Padding.Items.smallScreenPadding,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.also_you_would_like_drinks),
                            style = MaterialTheme.typography.titleSmall
                        )

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            itemsIndexed(state.randomCoffeeDrinks) { _, item ->
                                Box(modifier = Modifier.wrapContentSize()) {
                                    CoffeeCard(
                                        coffee = item,
                                        modifier = Modifier
                                            .width(width = Size.Coffee.coffeeCardWidth),
                                        onLikeClick = {
                                            event(CoffeeViewModel.Event.AddToLiked(coffee = it))
                                        },
                                        onCoffeeClick = {
                                            output(CoffeeViewModel.Output.SelectCoffee(value = it))
                                            output(CoffeeViewModel.Output.NavigateTo(route = Screens.COFFEE_DRINK))
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.width(width = Padding.Items.mediumScreenPadding))
                            }
                        }

                        Spacer(modifier = Modifier.height(height = Padding.Screens.smallScreenPadding))
                    }
                }
            }

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
                        onClick = {
                            output(CoffeeViewModel.Output.PopBackStack)
                        }
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

                            event(CoffeeViewModel.Event.AddToLiked(coffee = state.selectedCoffee))
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
}

@Composable
@Preview(showBackground = true)
private fun CoffeeScreenPreview() {

    val array = LocalContext.current.resources.getStringArray(C.array.ingredients)
    val list = mutableListOf<Ingredient>()

    for (i in array.indices step 1) {
        list.add(
            i, Ingredient(
                id = i,
                name = array[i],
                iconId = 0
            )
        )
    }

    val mockCoffeeModel = Coffee(
        id = -1,
        name = "Капучино",
        imageResId = R.drawable.mock_coffee_drink,
        isLiked = 1,
        roastingLevel = "Средняя прожарка",
        description = "adsafdgehrtyjukil",
        ageRating = 12,
        type = "Капучино",
        ingredients = listOf(
            list[0],
            list[1],
            list[2],
            list[4],
        )
    )

    val viewModel = CoffeeViewModel(
        userDatabase = null,
        selectedCoffee = mockCoffeeModel,
        coffeeStorage = null,
        output = {}
    )

    CoffeeAppTheme {
        Surface {
            CoffeeScreen(viewModel, viewModel::onOutput, event = viewModel::onEvent)
        }
    }
}