package ru.plumsoftware.domain.usecases.drinks

import ru.plumsoftware.domain.models.CoffeeModel
import ru.plumsoftware.domain.repositories.CoffeeRepository

class GetRandomDrinkUseCase (private val coffeeRepository: CoffeeRepository) {
    fun execute() : CoffeeModel = coffeeRepository.getRandomDrink()
}