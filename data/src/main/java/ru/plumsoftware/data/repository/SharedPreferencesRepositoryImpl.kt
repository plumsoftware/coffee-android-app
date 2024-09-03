package ru.plumsoftware.data.repository

import android.content.Context
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.repositories.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(private val context: Context) : SharedPreferencesRepository {
    override fun getUser(): User {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)

        return User(
            name = sharedPreferences.getString(Constants.NAME_SP, "")!!,
            birthday = sharedPreferences.getLong(Constants.BIRTHDAY_SP, 1L),
            theme = sharedPreferences.getBoolean(Constants.THEME_SP, false),
            isFirst = sharedPreferences.getInt(Constants.ISFIRST_SP, 1),
            agreeDate = sharedPreferences.getLong(Constants.AGREE_DATE, 0L)
        )
    }

    override fun set(name: String) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.NAME_SP, name)
        editor.apply()
    }

    override fun set(birthday: Long) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(Constants.BIRTHDAY_SP, birthday)
        editor.apply()
    }

    override fun set(theme: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.THEME_SP, theme)
        editor.apply()
    }

    override fun set(isFirst: Int) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Constants.ISFIRST_SP, isFirst)
        editor.apply()
    }

    override fun setAgreeDate(agreeDate: Long) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(Constants.AGREE_DATE, agreeDate)
        editor.apply()
    }
}