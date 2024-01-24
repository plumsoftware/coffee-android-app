package ru.plumsoftware.domain.storage

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel
import ru.plumsoftware.domain.usecases.drinks.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.drinks.GetAllIngredientsUseCase
import ru.plumsoftware.domain.usecases.drinks.GetTagsUseCase
import ru.plumsoftware.domain.usecases.drinks.ToMatrixUseCase

class CoffeeStorage(
    private val getAllIngredientsUseCase: GetAllIngredientsUseCase,
    private val getAllDrinksUseCase: GetAllDrinksUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val toMatrixUseCase: ToMatrixUseCase
) {
    fun getI(): List<IngredientModel> {
        return getAllIngredientsUseCase.execute()
    }

    fun getD(): List<CoffeeModel> {
        return getAllDrinksUseCase.execute()
    }

    fun getT(): List<String> = getTagsUseCase.execute()

    fun toMatrix() = toMatrixUseCase.execute()
}