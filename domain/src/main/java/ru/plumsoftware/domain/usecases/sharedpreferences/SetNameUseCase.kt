package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SetNameUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(name: String) {
        sharedPreferencesRepository.set(name = name)
    }
}