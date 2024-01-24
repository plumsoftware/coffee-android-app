package ru.plumsoftware.domain.repositories

import ru.plumsoftware.domain.models.UserModel

interface SharedPreferencesRepository {
    fun getUser(): UserModel

    fun set(name: String)
    fun set(birthday: Long)
    fun set(theme: Boolean)
    fun set(isFirst: Int)
}