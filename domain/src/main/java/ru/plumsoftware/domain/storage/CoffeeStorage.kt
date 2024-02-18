package ru.plumsoftware.domain.storage

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.models.IngredientModel
import ru.plumsoftware.domain.usecases.drinks.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.drinks.GetAllIngredientsUIUseCase
import ru.plumsoftware.domain.usecases.drinks.GetAllIngredientsUseCase
import ru.plumsoftware.domain.usecases.drinks.GetRandomDrinkUseCase
import ru.plumsoftware.domain.usecases.drinks.GetTagsUseCase
import ru.plumsoftware.domain.usecases.drinks.ToMatrixUseCase

class CoffeeStorage(
    private val getAllIngredientsUseCase: GetAllIngredientsUseCase,
    private val getAllDrinksUseCase: GetAllDrinksUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val toMatrixUseCase: ToMatrixUseCase,
    private val getRandomDrinkUseCase: GetRandomDrinkUseCase,
    private val getAllIngredientsUIUseCase: GetAllIngredientsUIUseCase,
) {
    fun getI(): List<IngredientModel> {
        return getAllIngredientsUseCase.execute()
    }

    fun getD(): List<CoffeeModel> {
        return getAllDrinksUseCase.execute()
    }

    fun getIUI(): List<IngredientModel> {
        return getAllIngredientsUIUseCase.execute()
    }

    fun getR(): CoffeeModel = getRandomDrinkUseCase.execute()

    fun getT(): List<String> = getTagsUseCase.execute()

    fun toMatrix(list: List<CoffeeModel>) = toMatrixUseCase.execute(list = list)
}