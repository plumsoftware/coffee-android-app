package ru.plumsoftware.domain.storage

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel
import ru.plumsoftware.domain.usecases.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.GetAllIngredientsUseCase

class CoffeeStorage(
    private val getAllIngredientsUseCase: GetAllIngredientsUseCase,
    private val getAllDrinksUseCase: GetAllDrinksUseCase
) {
    fun getI(): List<IngredientModel> {
        return getAllIngredientsUseCase.execute()
    }

    fun getD() : List<CoffeeModel> {
        return getAllDrinksUseCase.execute()
    }
}