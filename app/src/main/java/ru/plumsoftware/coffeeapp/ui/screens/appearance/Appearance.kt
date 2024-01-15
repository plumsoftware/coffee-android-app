package ru.plumsoftware.coffeeapp.ui.screens.appearance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.Dividers
import ru.plumsoftware.coffeeapp.ui.components.PrimaryButton
import ru.plumsoftware.coffeeapp.ui.components.RButton
import ru.plumsoftware.coffeeapp.ui.components.ThemePreview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun Appearance() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Padding.Screens.smallScreenPadding)
    ) {
        Text(
            text = stringResource(id = R.string.appearance_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = Padding.Items.largeScreenPadding),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(80.dp))

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(
                space = Padding.Items.smallScreenPadding,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(all = Padding.Items.smallScreenPadding)
            ) {
                ThemePreview(colorScheme = LightColors)
                RButton(selected = remember { mutableStateOf(true) })
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(all = Padding.Items.smallScreenPadding)
            ) {
                ThemePreview(colorScheme = DarkColors)
                RButton(selected = remember { mutableStateOf(false) })
            }
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = Padding.Items.largeScreenPadding,
                alignment = Alignment.Bottom
            ), horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Dividers(selected = 0)
            PrimaryButton(onClick = {})
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AppearancePreview() {
    CoffeeAppTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Appearance()
        }
    }
}