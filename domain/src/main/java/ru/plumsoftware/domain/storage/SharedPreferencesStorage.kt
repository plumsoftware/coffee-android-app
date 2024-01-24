package ru.plumsoftware.domain.storage

import ru.plumsoftware.domain.models.UserModel
import ru.plumsoftware.domain.usecases.sharedpreferences.GetUserUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetBirthdayUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetIsFirstUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetNameUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetThemeUseCase

class SharedPreferencesStorage(
    private val getUserUseCase: GetUserUseCase,
    private val setBirthdayUseCase: SetBirthdayUseCase,
    private val setIsFirstUseCase: SetIsFirstUseCase,
    private val setNameUseCase: SetNameUseCase,
    private val setThemeUseCase: SetThemeUseCase
) {

    fun get(): UserModel = getUserUseCase.execute()

    fun set(birthday: Long) {
        setBirthdayUseCase.execute(birthday)
    }

    fun set(theme: Boolean) {
        setThemeUseCase.execute(theme)
    }

    fun set(isFirst: Int) {
        setIsFirstUseCase.execute(isFirst)
    }

    fun set(name: String) {
        setNameUseCase.execute(name)
    }
}