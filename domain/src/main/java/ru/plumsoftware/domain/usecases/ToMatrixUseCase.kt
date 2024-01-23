package ru.plumsoftware.domain.usecases

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.repositories.CoffeeRepository

class ToMatrixUseCase(private val coffeeRepository: CoffeeRepository) {
    fun execute(): List<List<CoffeeModel>> {
        return coffeeRepository.toMatrix()
    }
}