package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SetIsFirstUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(isFirst: Int) {
        sharedPreferencesRepository.set(isFirst = isFirst)
    }
}