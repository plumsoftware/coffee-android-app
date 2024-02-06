package ru.plumsoftware.domain.usecases.drinks

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.repositories.CoffeeRepository

class ToMatrixUseCase(private val coffeeRepository: CoffeeRepository) {
    fun execute(list: List<CoffeeModel>): List<List<CoffeeModel>> {
        return coffeeRepository.toMatrix(list = list)
    }
}