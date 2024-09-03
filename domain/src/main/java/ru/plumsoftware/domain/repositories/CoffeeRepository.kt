package ru.plumsoftware.domain.repositories

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel

interface CoffeeRepository {
    fun getAllIngredients(): List<IngredientModel>
    fun getAllIngredientsUI(): List<IngredientModel>
    fun getAllDrinks(): List<CoffeeModel>
    fun getTags(): List<String>
    fun getRandomDrink() : CoffeeModel

    fun toMatrix(list: List<CoffeeModel>): List<List<CoffeeModel>>
}