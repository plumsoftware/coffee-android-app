package ru.plumsoftware.coffeeapp.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
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
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.appearance.AppearanceViewModel
import ru.plumsoftware.coffeeapp.ui.screens.home.Home
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredients
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredientsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.profile.Profile
import ru.plumsoftware.coffeeapp.ui.screens.profile.ProfileViewModel
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.Ingredient
import ru.plumsoftware.data.models.User
import ru.plumsoftware.domain.storage.CoffeeStorage

class MainActivity : ComponentActivity(), KoinComponent {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDatabase by inject<UserDatabase>()
        val coffeeStorage by inject<CoffeeStorage>()

        setContent {
            Content(userDatabase = userDatabase, coffeeStorage = coffeeStorage)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Content(userDatabase: UserDatabase, coffeeStorage: CoffeeStorage) {
    val mainViewModel = MainViewModel(userDatabase = userDatabase)

    val mainState = mainViewModel.state.collectAsState()
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
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
                color = MaterialTheme.colorScheme.background,

                ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.HOME
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
                                            navController.navigate(route = if (output.user?.name?.isEmpty()!!) Screens.APPEARANCE else Screens.HOME)
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

                        val viewModel = IntolerableIngredientsViewModel(
                            intolerableIngredients = coffeeStorage.getI().map {
                                Ingredient(
                                    id = it.id,
                                    name = it.name,
                                    iconId = it.iconId
                                )
                            },
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
                    composable(route = Screens.HOME) {
                        Scaffold(
                            bottomBar = {
                                BottomNavBar(
                                    onClick = {
                                        navController.navigate(route = it)
                                    }
                                )
                            }
                        ) {
                            mainViewModel.onEvent(
                                MainViewModel.Event.ChangeStatusBarColor(
                                    statusBarColor = getExtendedColors().welcomeBackgroundColor
                                )
                            )

                            val matrix = coffeeStorage.toMatrix().map { list ->
                                list.map { item ->
                                    item as Coffee
                                }
                            }
                            val coffeeOfTheDay = coffeeStorage.getD().map {
                                it as Coffee
                            }[0]

                            Home(
                                coffeeOfTheDay = coffeeOfTheDay,
                                coffeeMatrix = matrix
                            )
                        }
                    }
                    composable(route = Screens.LIKED) {

                    }
                    composable(route = Screens.SETTINGS) {

                    }
                }
            }
        }
    }
}

private class Test : KoinComponent {

    val userDatabase by inject<UserDatabase>()
    val coffeeStorage by inject<CoffeeStorage>()

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun TestContentPreview() {
        Content(userDatabase = userDatabase, coffeeStorage = coffeeStorage)
    }
}