package ru.plumsoftware.coffeeapp.ui.screens.settings

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.fill_in.AgeHint
import ru.plumsoftware.coffeeapp.ui.components.buttons.RButton
import ru.plumsoftware.coffeeapp.ui.components.fill_in.DateDefaults
import ru.plumsoftware.coffeeapp.ui.components.fill_in.MaskVisualTransformation
import ru.plumsoftware.coffeeapp.ui.components.fill_in.TextField
import ru.plumsoftware.coffeeapp.ui.components.groups.BottomNavBar
import ru.plumsoftware.coffeeapp.ui.components.groups.ThemePreview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.data.models.User

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Settings(settingsViewModel: SettingsViewModel, onEvent: (SettingsViewModel.Event) -> Unit) {

    val state = settingsViewModel.state.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selected = 2,
                onClick = {
                    settingsViewModel.onOutput(SettingsViewModel.Output.NavigateTo(route = it))
                }
            )
        }
    ) {
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
                    text = state.name,
                    label = stringResource(id = R.string.name_placeholder),
                    onValueChange = {
                        onEvent(SettingsViewModel.Event.ChangeName(name = it))
                    },
                )
                TextField(
                    text = state.birthday,
                    label = stringResource(id = R.string.age_placeholder),
                    mask = MaskVisualTransformation(DateDefaults.DATE_MASK),
                    onValueChange = {
                        onEvent(SettingsViewModel.Event.ChangeBirthday(birthday = it))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = stringResource(id = R.string.calendar_content_description)
                        )
                    },
                    trailingIcon = {
                        AgeHint(age = state.age)
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
                        RButton(selected = !state.useDark, onClick = {
                            onEvent(
                                SettingsViewModel.Event.ChangeRadioButton(
                                    useDark = false,
                                    targetColorScheme = LightColors
                                )
                            )
                            settingsViewModel.onOutput(
                                SettingsViewModel.Output.ChangeTheme(
                                    useDark = false,
                                    targetColorScheme = LightColors
                                )
                            )
                        })
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
                        RButton(selected = state.useDark, onClick = {
                            onEvent(
                                SettingsViewModel.Event.ChangeRadioButton(
                                    useDark = true,
                                    targetColorScheme = DarkColors
                                )
                            )
                            settingsViewModel.onOutput(
                                SettingsViewModel.Output.ChangeTheme(
                                    useDark = true,
                                    targetColorScheme = DarkColors
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsPreview() {

    val viewModel = SettingsViewModel(
        sharedPreferencesStorage = null,
        user = User(),
        {}
    )

    CoffeeAppTheme(useDarkTheme = false) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            Settings(
                settingsViewModel = viewModel,
                onEvent = viewModel::onEvent
            )
        }
    }
}