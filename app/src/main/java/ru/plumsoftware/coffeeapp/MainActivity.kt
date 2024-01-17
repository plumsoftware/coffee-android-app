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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.coffeeapp.ui.screens.appearance.Appearance
import ru.plumsoftware.coffeeapp.ui.screens.ingredients.IntolerableIngredients
import ru.plumsoftware.coffeeapp.ui.screens.profile.Profile
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.data.models.Ingredient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val targetColor by remember {
                mutableStateOf(LightColors)
            }
            val systemUiController = rememberSystemUiController()
            val color = targetColor.background

            Crossfade(
                targetState = targetColor,
                animationSpec = tween(400),
                label = "change theme"
            ) { colorScheme ->

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = color,
                    )
                    systemUiController.setNavigationBarColor(
                        color = color
                    )
                }

                CoffeeAppTheme(useDarkTheme = colorScheme != LightColors) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val array: Array<String> = stringArrayResource(R.array.ingredients)
                        val ingredients = mutableListOf<Ingredient>()
                        var id = 0

                        array.forEach { ingredient ->
                            ingredients.add(Ingredient(id = id, name = ingredient))
                            id++
                        }

                        IntolerableIngredients(
                            ingredients = ingredients.toList(),
                            firstSetup = false
                        )
                    }
                }
            }
        }
    }
}