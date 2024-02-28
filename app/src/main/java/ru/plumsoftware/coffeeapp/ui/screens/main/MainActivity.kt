package ru.plumsoftware.coffeeapp.ui.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.appearance.AppearanceViewModel
import ru.plumsoftware.coffeeapp.ui.screens.coffee.CoffeeScreen
import ru.plumsoftware.coffeeapp.ui.screens.coffee.CoffeeViewModel
import ru.plumsoftware.coffeeapp.ui.screens.home.Home
import ru.plumsoftware.coffeeapp.ui.screens.home.HomeViewModel
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredients
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredientsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.liked.Liked
import ru.plumsoftware.coffeeapp.ui.screens.liked.LikedViewModel
import ru.plumsoftware.coffeeapp.ui.screens.name.Profile
import ru.plumsoftware.coffeeapp.ui.screens.name.NameViewModel
import ru.plumsoftware.coffeeapp.ui.screens.search.Search
import ru.plumsoftware.coffeeapp.ui.screens.search.SearchViewModel
import ru.plumsoftware.coffeeapp.ui.screens.settings.Settings
import ru.plumsoftware.coffeeapp.ui.screens.settings.SettingsViewModel
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreen
import ru.plumsoftware.coffeeapp.ui.screens.splash.SplashScreenViewModel
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors
import ru.plumsoftware.data.database.UserDatabase
import ru.plumsoftware.data.models.Ingredient
import ru.plumsoftware.domain.storage.CoffeeStorage
import ru.plumsoftware.domain.storage.SharedPreferencesStorage

class MainActivity : ComponentActivity(), KoinComponent {

    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    private var appOpenAd: AppOpenAd? = null
    private var appOpenAdLoader: AppOpenAdLoader? = null
    private val AD_UNIT_ID = "demo-appopenad-yandex"
    private val adRequestConfiguration = AdRequestConfiguration.Builder(AD_UNIT_ID).build()
    private val appOpenAdEventListener = AdEventListener()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDatabase by inject<UserDatabase>()
        val coffeeStorage by inject<CoffeeStorage>()
        val sharedPreferencesStorage by inject<SharedPreferencesStorage>()

        MobileAds.initialize(this) {}

        setContent {
            Content(
                userDatabase = userDatabase,
                coffeeStorage = coffeeStorage,
                sharedPreferencesStorage = sharedPreferencesStorage,
                onBack = {
                    this.finish()
                }
            )
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun Content(
        userDatabase: UserDatabase,
        coffeeStorage: CoffeeStorage,
        sharedPreferencesStorage: SharedPreferencesStorage,
        onBack: () -> Unit
    ) {

        val systemUiController = rememberSystemUiController()
        val navController = rememberNavController()

        val mainViewModel
                by remember {
                    mutableStateOf(
                        MainViewModel(
                            sharedPreferencesStorage = sharedPreferencesStorage,
                            vibrator = App.INSTANCE.getSystemService(
                                Context.VIBRATOR_SERVICE
                            ) as Vibrator,
                            output = { output ->
                                when (output) {
                                    is MainViewModel.Output.NavigateTo -> {
                                        navController.navigate(route = output.route)
                                    }
                                }
                            })
                    )
                }
        val mainState = mainViewModel.state.collectAsState().value
        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentBackStackEntry?.destination?.route == Screens.SPLASH)
                        onBack()
                }
            }
        }

        if (mainState.user.isFirst != 0) {
            mainViewModel.onEvent(MainViewModel.Event.ChangeLoadingState(value = true))
            appOpenAdLoader = AppOpenAdLoader(this@MainActivity)

            val appOpenAdLoadListener = object : AppOpenAdLoadListener {
                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                    // The ad was loaded successfully. Now you can show loaded ad.
                    this@MainActivity.appOpenAd = appOpenAd
                    showAppOpenAd()
                    mainViewModel.onEvent(MainViewModel.Event.ChangeLoadingState(value = false))
                    Log.i("Yandex", appOpenAd.toString())
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                    // Ad failed to load with AdRequestError.
                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                    Log.i("Yandex", adRequestError.toString())
                    mainViewModel.onEvent(MainViewModel.Event.ChangeLoadingState(value = false))
                }
            }
            appOpenAd?.setAdEventListener(appOpenAdEventListener)
            appOpenAdLoader?.setAdLoadListener(appOpenAdLoadListener)
        }
        // On every successful composition, update the callback with the `enabled` value
        SideEffect {
            backCallback.isEnabled = true
        }
        val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
            "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
        }.onBackPressedDispatcher
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner, backDispatcher) {
            // Add callback to the backDispatcher
            backDispatcher.addCallback(lifecycleOwner, backCallback)
            // When the effect leaves the Composition, remove the callback
            onDispose {
                backCallback.remove()
            }
        }

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

                if (mainState.isAdsLoading)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
                    }
                NavHost(
                    navController = navController,
                    startDestination = Screens.SPLASH,
                    enterTransition = {
                        when (initialState.destination.route) {
                            Screens.COFFEE_DRINK ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(800)
                                )

                            else ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Screens.COFFEE_DRINK ->
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(800)
                                )

                            else ->
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(0)
                                )
                        }
                    },
                    popEnterTransition = {
                        when (initialState.destination.route) {
                            Screens.COFFEE_DRINK ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(800)
                                )

                            else -> slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(0)
                            )
                        }
                    },
                    popExitTransition = {
                        when (targetState.destination.route) {
                            Screens.COFFEE_DRINK ->
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(800)
                                )

                            else -> slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(0)
                            )
                        }
                    }
                ) {
                    composable(route = Screens.SPLASH) {
                        val viewModel =
                            SplashScreenViewModel(
                                sharedPreferencesStorage = sharedPreferencesStorage,
                                output = { output ->
                                    when (output) {
                                        is SplashScreenViewModel.Output.GetUser -> {
                                            navController.navigate(route = if (output.isFirst) Screens.APPEARANCE else Screens.HOME)
                                        }
                                    }
                                }
                            )

                        SplashScreen(
                            splashScreenViewModel = viewModel
                        )
                    }
                    composable(route = Screens.APPEARANCE) {
                        val viewModel =
                            AppearanceViewModel(
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
                        val viewModel =
                            NameViewModel(
                                sharedPreferencesStorage = sharedPreferencesStorage,
                                output = { output ->
                                    when (output) {
                                        NameViewModel.Output.Go -> {
                                            navController.navigate(route = Screens.INGREDIENTS)
                                        }
                                    }
                                }
                            )

                        Profile(
                            nameViewModel = viewModel,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable(route = Screens.INGREDIENTS) {
                        val viewModel =
                            IntolerableIngredientsViewModel(
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
                        mainViewModel.onEvent(MainViewModel.Event.ChangeNavBarColor(navBarColor = MaterialTheme.colorScheme.background))

                        val viewModel =
                            HomeViewModel(
                                coffeeStorage = coffeeStorage,
                                userDatabase = userDatabase,
                                isAdsLoading = false,
                                name = mainState.name,
                                age = mainState.age,
                                output = { output ->
                                    when (output) {
                                        is HomeViewModel.Output.NavigateTo -> {
                                            navController.navigate(route = output.route)
                                        }

                                        is HomeViewModel.Output.SelectCoffee -> {
                                            mainViewModel.onEvent(
                                                MainViewModel.Event.SelectCoffeeDrink(
                                                    value = output.value
                                                )
                                            )
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
                        mainViewModel.onEvent(MainViewModel.Event.SetUser)
                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = MaterialTheme.colorScheme.background
                            )
                        )
                        mainViewModel.onEvent(MainViewModel.Event.ChangeNavBarColor(navBarColor = MaterialTheme.colorScheme.background))

                        val viewModel = LikedViewModel(
                            age = mainState.age,
                            coffeeStorage = coffeeStorage,
                            userDatabase = userDatabase,
                            output = { output ->
                                when (output) {
                                    is LikedViewModel.Output.NavigateTo -> {
                                        navController.navigate(
                                            route = output.route
                                        )
                                    }

                                    is LikedViewModel.Output.SelectCoffee -> {
                                        mainViewModel.onEvent(
                                            MainViewModel.Event.SelectCoffeeDrink(
                                                value = output.value
                                            )
                                        )
                                    }
                                }
                            }
                        )
                        Liked(
                            likedViewModel = viewModel,
                            onEvent = viewModel::onEvent,
                            onOutput = viewModel::onOutput
                        )
                    }
                    composable(route = Screens.SETTINGS) {
                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = MaterialTheme.colorScheme.background
                            )
                        )
                        mainViewModel.onEvent(MainViewModel.Event.ChangeNavBarColor(navBarColor = MaterialTheme.colorScheme.background))

                        val viewModel =
                            SettingsViewModel(
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
                        mainViewModel.onEvent(MainViewModel.Event.SetUser)
                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = MaterialTheme.colorScheme.background
                            )
                        )
                        mainViewModel.onEvent(MainViewModel.Event.ChangeNavBarColor(navBarColor = MaterialTheme.colorScheme.background))

                        val viewModel =
                            SearchViewModel(
                                age = mainState.age,
                                userDatabase = userDatabase,
                                coffeeStorage = coffeeStorage,
                                tag = "",
                                output = { output ->
                                    when (output) {
                                        is SearchViewModel.Output.NavigateTo -> {
                                            navController.navigate(route = output.route)
                                        }

                                        is SearchViewModel.Output.SelectCoffee -> {
                                            mainViewModel.onEvent(
                                                MainViewModel.Event.SelectCoffeeDrink(
                                                    value = output.value
                                                )
                                            )
                                        }
                                    }
                                }
                            )

                        Search(searchViewModel = viewModel, onEvent = viewModel::onEvent)
                    }
                    composable(route = Screens.COFFEE_DRINK) {
                        interstitialAdLoader = InterstitialAdLoader(this@MainActivity).apply {
                            setAdLoadListener(object : InterstitialAdLoadListener {
                                override fun onAdLoaded(ad: InterstitialAd) {
                                    interstitialAd = ad
                                    Log.i("YandexADS", ad.toString())
                                }

                                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                                    // Ad failed to load with AdRequestError.
                                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                                    Log.i("YandexADS", adRequestError.toString())
                                }
                            })
                        }
                        loadInterstitialAd()

                        mainViewModel.onEvent(MainViewModel.Event.SetUser)
                        mainViewModel.onEvent(
                            MainViewModel.Event.ChangeStatusBarColor(
                                statusBarColor = Color.Transparent
                            )
                        )
                        mainViewModel.onEvent(MainViewModel.Event.ChangeNavBarColor(navBarColor = Color.Transparent))

                        val viewModel = CoffeeViewModel(
                            userDatabase = userDatabase,
                            coffeeStorage = coffeeStorage,
                            selectedCoffee = mainState.selectedCoffee,
                            age = mainState.age,
                            output = { output ->
                                when (output) {
                                    is CoffeeViewModel.Output.NavigateTo -> {
                                        navController.navigate(route = output.route)
                                    }

                                    is CoffeeViewModel.Output.SelectCoffee -> {
                                        mainViewModel.onEvent(
                                            MainViewModel.Event.SelectCoffeeDrink(
                                                value = output.value
                                            )
                                        )
                                    }

                                    CoffeeViewModel.Output.PopBackStack -> {
                                        showAd(navController = navController)
                                    }
                                }
                            }
                        )

                        CoffeeScreen(
                            coffeeViewModel = viewModel,
                            output = viewModel::onOutput,
                            event = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }

    private fun loadInterstitialAd() {
        val adRequestConfiguration =
            AdRequestConfiguration.Builder("demo-interstitial-yandex").build()
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    private fun showAd(navController: NavController) {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {
                    // Called when ad is shown.
                }

                override fun onAdFailedToShow(adError: AdError) {
                    // Called when an InterstitialAd failed to show.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    navController.popBackStack()
                }

                override fun onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    navController.popBackStack()
                }

                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
                    // Called when an impression is recorded for an ad.
                }
            })
            show(this@MainActivity)
        }
    }

    private fun loadAppOpenAd() {
        appOpenAdLoader?.loadAd(adRequestConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        destroyInterstitialAd()
    }

    private fun destroyInterstitialAd() {
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }

    private inner class AdEventListener : AppOpenAdEventListener {
        override fun onAdShown() {
            // Called when ad is shown.
        }

        override fun onAdFailedToShow(adError: AdError) {
            // Called when ad failed to show.
        }

        override fun onAdDismissed() {
            // Called when ad is dismissed.
            // Clean resources after dismiss and preload new ad.
            clearAppOpenAd()
            loadAppOpenAd()
        }

        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
        }

        override fun onAdImpression(impressionData: ImpressionData?) {
            // Called when an impression is recorded for an ad.
            // Get Impression Level Revenue Data in argument.
        }
    }

    private fun showAppOpenAd() {
        appOpenAd?.show(this@MainActivity)
    }

    private fun clearAppOpenAd() {
        appOpenAd?.setAdEventListener(null)
        appOpenAd = null
    }
}
