package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SetAgreeDateUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(agreeDate: Long) {
        sharedPreferencesRepository.setAgreeDate(agreeDate = agreeDate)
    }
}