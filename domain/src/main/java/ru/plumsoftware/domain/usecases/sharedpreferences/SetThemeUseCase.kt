package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SetThemeUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(theme: Boolean) {
        sharedPreferencesRepository.set(theme = theme)
    }
}