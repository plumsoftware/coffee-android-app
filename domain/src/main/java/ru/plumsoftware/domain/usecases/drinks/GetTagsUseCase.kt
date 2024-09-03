package ru.plumsoftware.domain.usecases.drinks

import ru.plumsoftware.domain.repositories.CoffeeRepository

class GetTagsUseCase(private val coffeeRepository: CoffeeRepository) {
    fun execute(): List<String> {
        return coffeeRepository.getTags()
    }
}