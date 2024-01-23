package ru.plumsoftware.domain.repositories

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel

interface CoffeeRepository {
    fun getAllIngredients(): List<IngredientModel>
    fun getAllDrinks(): List<CoffeeModel>
    fun getTags(): List<String>

    fun toMatrix(): List<List<CoffeeModel>>
}