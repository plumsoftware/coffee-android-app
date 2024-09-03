package ru.plumsoftware.coffeeapp.utilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.coffee.R
import ru.plumsoftware.data.models.Coffee

@Composable
fun getAllCoffee(): List<Coffee> {

    val ing = getAllIngredients()

    return listOf(
        Coffee(
            id = 0,
            name = stringArrayResource(id = R.array.coffee_names)[0],
            imageResId = R.drawable.cappuchino_classic,
            type = stringResource(id = R.string.cappuccino),
            isLiked = 0, // TODO()
            roastingLevel = stringArrayResource(id = R.array.roasting_levels)[1],
            description = stringArrayResource(id = R.array.descriptions)[0],
            ageRating = 12,
            ingredients = listOf(
                ing[0],
                ing[1]
            )
        )
    )
}