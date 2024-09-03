package ru.plumsoftware.domain.usecases.drinks

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.repositories.CoffeeRepository

class GetAllDrinksUseCase(private val coffeeRepository: CoffeeRepository) {
    fun execute(): List<CoffeeModel> {
        return coffeeRepository.getAllDrinks()
    }
}