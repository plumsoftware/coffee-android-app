package ru.plumsoftware.coffeeapp

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.coffeeapp.ui.screens.MainViewModel
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.User

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel()

        setContent {
            val systemUiController = rememberSystemUiController()
            val navController = rememberNavController()

            Crossfade(
                targetState = mainViewModel.targetColor,
                animationSpec = tween(400),
                label = "change theme"
            ) { colorScheme ->

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = mainViewModel.statusBarColor,
                    )
                    systemUiController.setNavigationBarColor(
                        color = mainViewModel.navColor
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
                                        output = { output ->
                                            when (output) {
                                                is SplashScreenViewModel.Output.GetUser -> {
                                                    navController.navigate(route = if (output.user?.name?.isEmpty()!!) Screens.APPEARANCE else Screens.HOME)
                                                }

                                                else -> {}
                                            }
                                        }
                                    )
                                )
                            }
                            composable(route = Screens.APPEARANCE) {
                                Appearance()
                            }
                        }
                    }
                }
            }
        }
    }
}