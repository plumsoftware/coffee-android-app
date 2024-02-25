package ru.plumsoftware.coffeeapp.ui.screens.main

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.ImpressionData
import ru.plumsoftware.coffeeapp.application.App
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.Coffee
import ru.plumsoftware.data.models.User

data class MainState(
    val user: User = User(),
    val useDark: Boolean = user.theme,
    val targetColorScheme: ColorScheme = if (user.theme) DarkColors else LightColors,
    val name: String = "",
    val navColor: Color = targetColorScheme.background,
    val statusBarColor: Color = targetColorScheme.background,
    val currentScreen: Screens.Screens = Screens.Screens.Home,
    val selectedCoffee: Coffee = Coffee(
        id = 0,
        name = "",
        imageResId = 0,
        type = "",
        isLiked = 0,
        roastingLevel = "",
        description = "",
        ageRating = 0,
        ingredients = emptyList()
    ),
    val age: Int = 1,
    val isAdsLoading: Boolean,
    var myAppOpenAd: AppOpenAd? = null,
    val appOpenAdEventListener: AppOpenAdEventListener = object : AppOpenAdEventListener {
        override fun onAdShown() {
            // Called when ad is shown.
        }

        override fun onAdFailedToShow(adError: AdError) {
            // Called when ad failed to show.
        }

        override fun onAdDismissed() {
            // Called when ad is dismissed.
            // Clean resources after dismiss and preload new ad.
            myAppOpenAd?.setAdEventListener(null)
        }

        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
        }

        override fun onAdImpression(impressionData: ImpressionData?) {
            // Called when an impression is recorded for an ad.
            // Get Impression Level Revenue Data in argument.
        }
    },
    val appOpenAdLoader: AppOpenAdLoader = AppOpenAdLoader(App.INSTANCE),
    val adRequestConfigurationOpenAds: AdRequestConfiguration = AdRequestConfiguration.Builder("R-M-6292390-1").build()
)
