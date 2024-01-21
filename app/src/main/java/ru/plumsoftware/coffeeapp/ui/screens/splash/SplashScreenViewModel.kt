package ru.plumsoftware.coffeeapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class SplashScreenViewModel(
    private val output: (Output) -> Unit,
    private val userDatabase: UserDatabase?,
) : ViewModel(), KoinComponent {

    suspend fun getUser(): User? {
        return userDatabase!!.dao.get()
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class GetUser(val user: User?) : Output()
    }
}