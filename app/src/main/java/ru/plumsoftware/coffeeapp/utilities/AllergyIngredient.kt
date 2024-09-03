package ru.plumsoftware.coffeeapp.utilities

import android.content.Context
import ru.plumsoftware.coffee.R
import ru.plumsoftware.data.models.Ingredient

class AllergyIngredient(
    val ingredient: Ingredient,
    val with: Int,
    val without: Int
) {
    companion object {
        fun getAllergyIngredients(context: Context): List<AllergyIngredient> {
            val milk = Ingredient(
                id = 1,
                name = context.resources.getStringArray(R.array.ingredients)[1],
                iconId = 0
            )

            return listOf(
                AllergyIngredient(
                    ingredient = milk,
                    with = ru.plumsoftware.coffeeapp.R.string.with_milk,
                    without = ru.plumsoftware.coffeeapp.R.string.without_milk
                )
            )
        }
    }
}