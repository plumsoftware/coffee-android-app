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
import androidx.compose.ui.res.stringArrayResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.coffee.R
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.appearance.AppearanceViewModel
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredients
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredientsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.profile.Profile
import ru.plumsoftware.coffeeapp.ui.screens.profile.ProfileViewModel
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Ingredient
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
                Log.v("TAG", userDatabase.dao.getUser().toString())
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
                                                    navController.navigate(route = if (output.user?.name?.isEmpty()!!) Screens.APPEARANCE else Screens.INGREDIENTS)
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
                                                navController.navigate(route = Screens.INGREDIENTS)
                                            }
                                        }
                                    }
                                )
                                Profile(
                                    profileViewModel = viewModel,
                                    onEvent = viewModel::onEvent
                                )
                            }
                            composable(route = Screens.INGREDIENTS) {

                                val array: Array<String> = stringArrayResource(R.array.ingredients)
                                val ingredients = mutableListOf<Ingredient>()
                                var id = 0

                                array.forEach { ingredient ->
                                    ingredients.add(
                                        Ingredient(id = id, name = ingredient),
                                    )
                                    id++
                                }

                                val viewModel = IntolerableIngredientsViewModel(
                                    intolerableIngredients = ingredients,
                                    userDatabase = userDatabase,
                                    output = { output ->
                                        when (output) {
                                            is IntolerableIngredientsViewModel.Output.Go -> {
                                            }
                                        }
                                    }
                                )

                                IntolerableIngredients(
                                    intolerableIngredientsViewModel = viewModel,
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