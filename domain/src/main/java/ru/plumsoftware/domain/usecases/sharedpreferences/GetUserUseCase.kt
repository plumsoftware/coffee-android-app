package ru.plumsoftware.domain.usecases.sharedpreferences

import ru.plumsoftware.domain.models.UserModel
import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class GetUserUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): UserModel = sharedPreferencesRepository.getUser()
}