package ru.plumsoftware.coffeeapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class SplashScreenViewModel(
    private val output: (SplashScreenViewModel.Output) -> Unit
) : ViewModel(), KoinComponent {
    private val userDatabase by inject<UserDatabase>()

    suspend fun getUser(): User? {
        return userDatabase.dao.get()
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class GetUser(val user: User?) : Output()
    }
}