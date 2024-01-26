package ru.plumsoftware.coffeeapp.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import ru.plumsoftware.coffeeapp.ui.screens.home.Home
import ru.plumsoftware.coffeeapp.ui.screens.home.HomeViewModel
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredients
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredientsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.profile.Profile
import ru.plumsoftware.coffeeapp.ui.screens.profile.ProfileViewModel
import ru.plumsoftware.coffeeapp.ui.screens.search.Search
import ru.plumsoftware.coffeeapp.ui.screens.search.SearchViewModel
import ru.plumsoftware.coffeeapp.ui.screens.settings.Settings
import ru.plumsoftware.coffeeapp.ui.screens.settings.SettingsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.Ingredient
import ru.plumsoftware.domain.storage.CoffeeStorage
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class MainActivity : ComponentActivity(), KoinComponent {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDatabase by inject<UserDatabase>()
        val coffeeStorage by inject<CoffeeStorage>()
        val sharedPreferencesStorage by inject<SharedPreferencesStorage>()

        setContent {
            Content(
                userDatabase = userDatabase,
                coffeeStorage = coffeeStorage,
                sharedPreferencesStorage = sharedPreferencesStorage
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Content(
    userDatabase: UserDatabase,
    coffeeStorage: CoffeeStorage,
    sharedPreferencesStorage: SharedPreferencesStorage
) {
    val mainViewModel =
        MainViewModel(sharedPreferencesStorage = sharedPreferencesStorage, context = App.INSTANCE)

    val mainState = mainViewModel.state.collectAsState().value
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
    Crossfade(
        targetState = mainState.targetColorScheme,
        animationSpec = tween(400),
        label = "change theme"
    ) { colorScheme ->

        SideEffect {
            systemUiController.setStatusBarColor(
                color = mainState.statusBarColor,
            )
            systemUiController.setNavigationBarColor(
                color = mainState.navColor
            )
        }

        CoffeeAppTheme(useDarkTheme = mainState.useDark) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,

                ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.SPLASH
                ) {
                    composable(route = Screens.SPLASH) {
                        SplashScreen(
                            splashScreenViewModel = SplashScreenViewModel(
                                sharedPreferencesStorage = sharedPreferencesStorage,
                                output = { output ->
                                    when (output) {
                                        is SplashScreenViewModel.Output.GetUser -> {
                                            navController.navigate(route = if (output.isFirst) Screens.APPEARANCE else Screens.HOME)
                                        }
                                    }
                                }
                            )
                        )
                    }
                    composable(route = Screens.APPEARANCE) {
                        val viewModel = AppearanceViewModel(
                            sharedPreferencesStorage = sharedPreferencesStorage,
                            output = { output ->
                                when (output) {
                                    is AppearanceViewModel.Output.ChangeTheme -> {
                                        mainViewModel.onEvent(
                                            MainViewModel.Event.ChangeColorScheme(
                                                targetColorScheme = output.targetColorScheme,
                                                useDark = output.useDark
                                            )
                                        )
                                        mainViewModel.onEvent(MainViewModel.Event.Vibrate)
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
                            sharedPreferencesStorage = sharedPreferencesStorage,
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
                            ingredients = coffeeStorage.getI().map {
                                Ingredient(
                                    id = it.id,
                                    name = it.name,
                                    iconId = it.iconId
                                )
                            },
                            userDatabase = userDatabase,
                            sharedPreferencesStorage = sharedPreferencesStorage,
                            output = { output ->
                                when (output) {
                                    is IntolerableIngredientsViewModel.Output.Go -> {
                                        navController.navigate(route = Screens.HOME)
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
                        mainViewModel.onEvent(MainViewModel.Event.SetUser)
                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = getExtendedColors().welcomeBackgroundColor
                            )
                        )

                        val viewModel = HomeViewModel(
                            coffeeStorage = coffeeStorage,
                            userDatabase = userDatabase,
                            name = mainState.name,
                            output = { output ->
                                when (output) {
                                    is HomeViewModel.Output.NavigateTo -> {
                                        navController.navigate(route = output.route)
                                    }
                                }
                            }
                        )

                        Home(
                            homeViewModel = viewModel,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable(route = Screens.LIKED) {

                    }
                    composable(route = Screens.SETTINGS) {

                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = MaterialTheme.colorScheme.background
                            )
                        )

                        val viewModel = SettingsViewModel(
                            sharedPreferencesStorage = sharedPreferencesStorage,
                            user = mainState.user,
                            output = { output ->
                                when (output) {
                                    is SettingsViewModel.Output.ChangeTheme -> {
                                        mainViewModel.onEvent(
                                            MainViewModel.Event.ChangeColorScheme(
                                                targetColorScheme = output.targetColorScheme,
                                                useDark = output.useDark
                                            )
                                        )
                                        mainViewModel.onEvent(MainViewModel.Event.Vibrate)
                                    }

                                    is SettingsViewModel.Output.NavigateTo -> {
                                        navController.navigate(route = output.route)
                                    }
                                }
                            }
                        )

                        Settings(settingsViewModel = viewModel, onEvent = viewModel::onEvent)
                    }
                    composable(route = Screens.SEARCH) {
                        val viewModel = SearchViewModel(
                            coffeeList = coffeeStorage.getD().map { it as Coffee },
                            tag = "",
                            output = { output ->
                                when (output) {
                                    else -> {}
                                }
                            }
                        )
                        Search(searchViewModel = viewModel, onEvent = viewModel::onEvent)
                    }
                }
            }
        }
    }
}
