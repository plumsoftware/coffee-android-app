package ru.plumsoftware.coffeeapp.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.AgeHint
import ru.plumsoftware.coffeeapp.ui.components.RButton
import ru.plumsoftware.coffeeapp.ui.components.TextField
import ru.plumsoftware.coffeeapp.ui.components.ThemePreview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun Settings() {

    var text1 by remember {
        mutableStateOf("Имя")
    }

    var text2 by remember {
        mutableStateOf("11.07.2003")
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.Items.mediumScreenPadding,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = Padding.Screens.smallScreenPadding)
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = Padding.Items.largeScreenPadding),
            textAlign = TextAlign.Center
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = Padding.Items.smallScreenPadding,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            TextField(
                text = text1,
                label = stringResource(id = R.string.name_placeholder),
                onValueChange = {
                    text1 = it
                },
                leadingIcon = {},
                trailingIcon = {}
            )
            TextField(
                text = text2,
                label = stringResource(id = R.string.age_placeholder),
                onValueChange = {
                    text2 = it
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_icon),
                        contentDescription = stringResource(id = R.string.calendar_content_description)
                    )
                },
                trailingIcon = {
                    AgeHint(age = 20)
                }
            )

            Spacer(modifier = Modifier.height(height = Padding.Items.smallScreenPadding))

            Text(
                text = stringResource(id = R.string.appearance),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Start
            )

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
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsPreview() {
    CoffeeAppTheme(useDarkTheme = false) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            Settings()
        }
    }
}