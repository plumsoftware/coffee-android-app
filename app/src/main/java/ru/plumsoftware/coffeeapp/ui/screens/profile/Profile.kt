package ru.plumsoftware.coffeeapp.ui.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import ru.plumsoftware.coffeeapp.ui.components.other.Dividers
import ru.plumsoftware.coffeeapp.ui.components.buttons.PrimaryButton
import ru.plumsoftware.coffeeapp.ui.components.fill_in.DateDefaults
import ru.plumsoftware.coffeeapp.ui.components.fill_in.MaskVisualTransformation
import ru.plumsoftware.coffeeapp.ui.components.fill_in.TextField
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@SuppressLint("SimpleDateFormat")
@Composable
fun Profile(
    profileViewModel: ProfileViewModel,
    onEvent: (ProfileViewModel.Event) -> Unit
) {

    val state = profileViewModel.state.collectAsState().value

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
                text = state.name,
                label = stringResource(id = R.string.name_placeholder),
                onValueChange = {
                    onEvent(ProfileViewModel.Event.ChangeName(name = it))
                }
            )
            TextField(
                text = state.birthday,
                label = stringResource(id = R.string.age_placeholder),
                mask = MaskVisualTransformation(DateDefaults.DATE_MASK),
                onValueChange = {
                    onEvent(ProfileViewModel.Event.ChangeBirthday(birthday = it))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar_icon),
                        contentDescription = stringResource(id = R.string.calendar_content_description)
                    )
                },
                trailingIcon = {
                    AgeHint(age = state.age)
                })
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
            PrimaryButton(onClick = {
                onEvent(
                    ProfileViewModel.Event.SaveData(
                        birthday = state.birthday,
                        name = state.name
                    )
                )
                profileViewModel.onOutput(ProfileViewModel.Output.Go)
            }, isActive = state.isActive)
        }
    }
}

@Composable
@Preview
private fun ProfilePreview() {
    CoffeeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Profile(
                profileViewModel = ProfileViewModel(
                    userDatabase = null,
                    {}
                ),
                onEvent = {}
            )
        }
    }
}