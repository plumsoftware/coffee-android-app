package ru.plumsoftware.data.repository

import android.content.Context
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.coffee.R as C
import ru.plumsoftware.data.models.Ingredient
import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel
import ru.plumsoftware.domain.repositories.CoffeeRepository

class CoffeeRepositoryImpl(private val context: Context) : CoffeeRepository {
    override fun getAllIngredients(): List<Ingredient> {

        val array = context.resources.getStringArray(C.array.ingredients)
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

        return list.toList()
    }

    override fun getAllIngredientsUI(): List<IngredientModel> {
        val allDrinks = getAllDrinks()
        val list = mutableListOf<Ingredient>()

        for (drink in allDrinks) {
            for (ingredient in drink.ingredients) {
                if (!list.contains(ingredient)) {
                    list.add(
                        ingredient as Ingredient
                    )
                }
            }
        }

        return list.toList()
    }

    override fun getAllDrinks(): List<CoffeeModel> {
        val allIngredients = getAllIngredients()
        val coffeeNames = context.resources.getStringArray(C.array.coffee_names)
        val roastingLevels = context.resources.getStringArray(C.array.roasting_levels)
        val descriptions = context.resources.getStringArray(C.array.descriptions)

        val list = mutableListOf<CoffeeModel>()

//        region::Cappuchino
        list.add(
            Coffee(
                id = 0,
                name = coffeeNames[0],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_classic,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[0],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[4],
                )
            )
        )
        list.add(
            Coffee(
                id = 1,
                name = coffeeNames[1],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_chocolate,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[1],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[4],
                    allIngredients[9],
                )
            )
        )
        list.add(
            Coffee(
                id = 2,
                name = coffeeNames[2],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_caramel,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[2],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[2],
                    allIngredients[4],
                    allIngredients[15],
                )
            )
        )
        list.add(
            Coffee(
                id = 3,
                name = coffeeNames[3],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_forest_nut,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[3],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[6],
                    allIngredients[61],
                )
            )
        )
        list.add(
            Coffee(
                id = 4,
                name = coffeeNames[4],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_vanila,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[4],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[6],
                    allIngredients[9],
                    allIngredients[8]
                )
            )
        )
        list.add(
            Coffee(
                id = 5,
                name = coffeeNames[5],
                ageRating = 14,
                imageResId = C.drawable.cappuchino_french,
                type = context.getString(C.string.cappuccino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[5],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[6],
                    allIngredients[21],
                    allIngredients[9]
                )
            )
        )
//        endregion

//        region::Latte
        list.add(
            Coffee(
                id = 6,
                name = coffeeNames[6],
                ageRating = 14,
                imageResId = C.drawable.latte_classic,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[6],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[7],
                    allIngredients[9],
                )
            )
        )
        list.add(
            Coffee(
                id = 7,
                name = coffeeNames[7],
                ageRating = 14,
                imageResId = C.drawable.vanilla_latte,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[7],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[7],
                    allIngredients[9],
                    allIngredients[8],
                    allIngredients[22],
                )
            )
        )
        list.add(
            Coffee(
                id = 8,
                name = coffeeNames[8],
                ageRating = 14,
                imageResId = C.drawable.latte_caramel,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[8],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[7],
                    allIngredients[9],
                    allIngredients[16],
                    allIngredients[22],
                )
            )
        )
        list.add(
            Coffee(
                id = 9,
                name = coffeeNames[9],
                ageRating = 14,
                imageResId = C.drawable.chocolate_latte,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[9],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[7],
                    allIngredients[6],
                    allIngredients[22],
                )
            )
        )
        list.add(
            Coffee(
                id = 10,
                name = coffeeNames[10],
                ageRating = 14,
                imageResId = C.drawable.latte_almond,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[10],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[88],
                    allIngredients[89],
                    allIngredients[22],
                    allIngredients[90],
                )
            )
        )
        list.add(
            Coffee(
                id = 11,
                name = coffeeNames[11],
                ageRating = 14,
                imageResId = C.drawable.latte_halvah,
                type = context.getString(C.string.latte),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[11],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[58]
                )
            )
        )

//        endregion

//        region::Moccachino
        list.add(
            Coffee(
                id = 12,
                name = coffeeNames[12],
                ageRating = 14,
                imageResId = C.drawable.mochacchino_classic,
                type = context.getString(C.string.mochacchino),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[12],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[21]
                )
            )
        )
//        endregion

//        region::Flat_white
        list.add(
            Coffee(
                id = 14,
                name = coffeeNames[14],
                ageRating = 14,
                imageResId = C.drawable.flat_white_classic,
                type = context.getString(C.string.flat_white),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[14],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[9],
                    allIngredients[7],
                )
            )
        )
//        endregion

//        region::Raf
        list.add(
            Coffee(
                id = 15,
                name = coffeeNames[15],
                ageRating = 14,
                imageResId = C.drawable.raf_classic,
                type = context.getString(C.string.raf),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[15],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[14],
                    allIngredients[8],
                )
            )
        )
        list.add(
            Coffee(
                id = 16,
                name = coffeeNames[16],
                ageRating = 14,
                imageResId = C.drawable.raf_honey,
                type = context.getString(C.string.raf),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[16],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[7],
                    allIngredients[14],
                    allIngredients[22],
                    allIngredients[24],
                )
            )
        )
        list.add(
            Coffee(
                id = 17,
                name = coffeeNames[17],
                ageRating = 14,
                imageResId = C.drawable.raf_caramel,
                type = context.getString(C.string.raf),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[17],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[2],
                    allIngredients[7],
                    allIngredients[17],
                    allIngredients[14],
                    allIngredients[22],
                )
            )
        )
        list.add(
            Coffee(
                id = 18,
                name = coffeeNames[18],
                ageRating = 14,
                imageResId = C.drawable.lavendar_raf,
                type = context.getString(C.string.raf),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[18],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[89],
                    allIngredients[103],
                    allIngredients[104],
                )
            )
        )
//        endregion

//        region::Glasse

//        endregion

//        region::Cold
        list.add(
            Coffee(
                id = 13,
                name = coffeeNames[13],
                ageRating = 14,
                imageResId = C.drawable.ice_mochacchino,
                type = context.getString(C.string.cold),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[13],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[1],
                    allIngredients[6],
                    allIngredients[22],
                    allIngredients[91],
                    allIngredients[92],
                )
            )
        )
        list.add(
            Coffee(
                id = 19,
                name = coffeeNames[19],
                ageRating = 14,
                imageResId = C.drawable.glasse,
                type = context.getString(C.string.cold),
                isLiked = 0,
                roastingLevel = roastingLevels[1],
                description = descriptions[19],
                ingredients = listOf(
                    allIngredients[0],
                    allIngredients[2],
                    allIngredients[9],
                    allIngredients[18],
                    allIngredients[92]
                )
            )
        )
//        endregion

        return list
    }

    override fun getTags(): List<String> {
        return context.resources.getStringArray(C.array.tag_list).toList()
    }

    override fun getRandomDrink(): CoffeeModel = getAllDrinks().random()

    override fun toMatrix(list: List<CoffeeModel>): List<List<CoffeeModel>> {
        return list.groupBy { it.type }.values.toList()
    }
}