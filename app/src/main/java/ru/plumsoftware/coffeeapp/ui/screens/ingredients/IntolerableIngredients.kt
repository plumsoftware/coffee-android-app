package ru.plumsoftware.coffeeapp.ui.screens.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.Dividers
import ru.plumsoftware.coffeeapp.ui.components.PrimaryButton
import ru.plumsoftware.coffeeapp.ui.components.PrimaryChip
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.data.models.Ingredient

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IntolerableIngredients(ingredients: List<Ingredient>, firstSetup: Boolean) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.TopCenter)
                .padding(
                    start = Padding.Screens.smallScreenPadding,
                    end = Padding.Screens.smallScreenPadding,
                )
        ) {
            item {
                Spacer(modifier = Modifier.height(height = if (firstSetup) Padding.Items.extraLargeScreenTopPadding else Padding.Items.extraLargeScreenTopPadding_2))
                FlowRow(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                ) {
                    ingredients.forEach {
                        val selected = remember {
                            mutableStateOf(false)
                        }
                        PrimaryChip(
                            selected = selected.value,
                            text = it.name,
                            onClick = { selected.value = !selected.value })
                        Spacer(modifier = Modifier.width(width = Padding.Items.smallScreenPadding))
                    }
                }
                Spacer(modifier = Modifier.height(height = Padding.Items.extraLargeScreenBottomPadding))
            }
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        if (firstSetup) Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background
                            )
                        ) else Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                Color.Transparent
                            )
                        )
                    )
                    .padding(all = Padding.Items.largeScreenPadding)
            ) {
                Text(
                    text = if (firstSetup) stringResource(id = R.string.name_register) else stringResource(
                        id = R.string.intolerable_ingredients
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = Padding.Items.largeScreenPadding),
                    textAlign = TextAlign.Center
                )
            }

            if (firstSetup)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background,
                                    Color.Transparent
                                )
                            )
                        )
                        .padding(horizontal = Padding.Items.largeScreenPadding)
                )
                {
                    Text(
                        text = stringResource(id = R.string.intolerable_ingredients),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Start
                    )
                }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
                    .padding(horizontal = Padding.Items.largeScreenPadding)
            ) {
                Dividers(selected = 2)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(all = Padding.Items.largeScreenPadding)
            ) {
                PrimaryButton(onClick = {}, isActive = true)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun IntolerableIngredientsPreview() {

    val array: Array<String> = stringArrayResource(R.array.ingredients)
    val ingredients = mutableListOf<Ingredient>()
    var id = 0

    array.forEach { ingredient ->
        ingredients.add(Ingredient(id = id, name = ingredient))
        id++
    }

    CoffeeAppTheme {
        IntolerableIngredients(
            ingredients = ingredients.toList(),
            firstSetup = true
        )
    }
}