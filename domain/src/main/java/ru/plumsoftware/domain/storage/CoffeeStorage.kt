package ru.plumsoftware.domain.storage

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel
import ru.plumsoftware.domain.usecases.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.GetAllIngredientsUseCase
import ru.plumsoftware.domain.usecases.GetTagsUseCase
import ru.plumsoftware.domain.usecases.ToMatrixUseCase

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