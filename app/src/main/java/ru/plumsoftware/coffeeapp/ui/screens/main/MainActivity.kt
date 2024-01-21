package ru.plumsoftware.coffeeapp.ui.screens.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.appearance.AppearanceViewModel
import ru.plumsoftware.coffeeapp.ui.screens.profile.Profile
import ru.plumsoftware.coffeeapp.ui.screens.profile.ProfileViewModel
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDatabase by inject<UserDatabase>()

        val mainViewModel = MainViewModel(userDatabase = userDatabase)

        setContent {
            val mainState = mainViewModel.state.collectAsState()
            val systemUiController = rememberSystemUiController()
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit, block = {
                Log.v("TAG", userDatabase.dao.get().toString())
            })

            Crossfade(
                targetState = mainState.value.targetColorScheme,
                animationSpec = tween(400),
                label = "change theme"
            ) { colorScheme ->

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = mainState.value.statusBarColor,
                    )
                    systemUiController.setNavigationBarColor(
                        color = mainState.value.navColor
                    )
                }

                CoffeeAppTheme(useDarkTheme = colorScheme != LightColors) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screens.SPLASH
                        ) {
                            composable(route = Screens.SPLASH) {
                                SplashScreen(
                                    splashScreenViewModel = SplashScreenViewModel(
                                        userDatabase = userDatabase,
                                        output = { output ->
                                            when (output) {
                                                is SplashScreenViewModel.Output.GetUser -> {
                                                    mainViewModel.onEvent(
                                                        MainViewModel.Event.SetUser(
                                                            user = output.user
                                                        )
                                                    )
                                                    navController.navigate(route = if (mainState.value.user?.name?.isEmpty()!!) Screens.APPEARANCE else Screens.HOME)
                                                    navController.clearBackStack(route = Screens.SPLASH)
                                                }
                                            }
                                        }
                                    )
                                )
                            }
                            composable(route = Screens.APPEARANCE) {
                                val viewModel = AppearanceViewModel(
                                    userDatabase = userDatabase,
                                    useDark = mainState.value.user?.theme!!,
                                    output = { output ->
                                        when (output) {
                                            is AppearanceViewModel.Output.ChangeTheme -> {
                                                mainViewModel.onEvent(
                                                    MainViewModel.Event.ChangeColorScheme(
                                                        targetColorScheme = output.targetColorScheme,
                                                        useDark = output.useDark
                                                    )
                                                )
                                            }

                                            AppearanceViewModel.Output.Go -> {
                                                navController.navigate(route = Screens.NAME)
                                            }
                                        }
                                    }
                                )
                                Appearance(
                                    appearanceViewModel = viewModel,
                                    onEvent = viewModel::onEvent
                                )
                            }
                            composable(route = Screens.NAME) {
                                val viewModel = ProfileViewModel(
                                    userDatabase = userDatabase,
                                    output = { output ->
                                        when (output) {
                                            ProfileViewModel.Output.Go -> {

                                            }
                                        }
                                    }
                                )
                                Profile(
                                    profileViewModel = viewModel,
                                    onEvent = viewModel::onEvent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}