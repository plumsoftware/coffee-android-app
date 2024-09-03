package ru.plumsoftware.coffeeapp.utilities

import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.data.models.Coffee

fun emptyCoffee(): Coffee {
    return Coffee(
        id = 0,
        name = "",
        imageResId = R.drawable.mock_coffee_drink,
        type = "",
        isLiked = 0,
        roastingLevel = "",
        description = "",
        ageRating = 0,
        ingredients = emptyList()
    )
}