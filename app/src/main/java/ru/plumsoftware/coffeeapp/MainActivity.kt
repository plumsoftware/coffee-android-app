package ru.plumsoftware.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val targetColor by remember {
                mutableStateOf(DarkColors)
            }
            val systemUiController = rememberSystemUiController()
            val navigationBarColor = targetColor.background
            val statusBarColor = getExtendedColors().welcomeBackgroundColor

            Crossfade(
                targetState = targetColor,
                animationSpec = tween(400),
                label = "change theme"
            ) { colorScheme ->

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                    )
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor
                    )
                }

                CoffeeAppTheme(useDarkTheme = colorScheme != LightColors) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                    }
                }
            }
        }
    }
}