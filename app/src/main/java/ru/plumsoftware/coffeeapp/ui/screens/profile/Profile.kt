package ru.plumsoftware.coffeeapp.ui.screens.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.Dividers
import ru.plumsoftware.coffeeapp.ui.components.PrimaryButton
import ru.plumsoftware.coffeeapp.ui.components.TextField
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun Profile() {

    var text by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Padding.Screens.smallScreenPadding)
    ) {
        Text(
            text = stringResource(id = R.string.name_register),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = Padding.Items.largeScreenPadding),
            textAlign = TextAlign.Center
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = Padding.Items.smallScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextField(
                text = text,
                label = stringResource(id = R.string.name_placeholder),
                onValueChange = {
                    text = it
                },
                leadingIcon = {})
            TextField(
                text = "",
                label = stringResource(id = R.string.age_placeholder),
                onValueChange = {},
                leadingIcon = {})
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
            Dividers(selected = 1)
            PrimaryButton(onClick = {})
        }
    }
}

@Composable
@Preview
private fun ProfilePreview() {
    CoffeeAppTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Profile()
        }
    }
}