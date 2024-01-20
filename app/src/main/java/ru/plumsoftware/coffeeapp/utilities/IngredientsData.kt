package ru.plumsoftware.coffeeapp.utilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.coffee.R as C
import ru.plumsoftware.data.models.Ingredient

@Composable
fun getAllIngredients(): List<Ingredient> {
    val a = stringArrayResource(id = C.array.ingredients)

    val list = mutableListOf<Ingredient>()

    for (i in 0..a.size step 1) {
        list.add(
            i, Ingredient(
                id = i,
                name = a[i],
                iconId = if (a[i] == stringResource(id = C.string.milk)) C.drawable.milk else 0
            )
        )
    }

    return list.toList()
}