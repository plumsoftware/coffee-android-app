package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SetBirthdayUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(birthday: Long) {
        sharedPreferencesRepository.set(birthday = birthday)
    }
}