package ru.plumsoftware.coffeeapp.ui.screens.name

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.asSharedFlow
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.components.fill_in.AgeHint
import ru.plumsoftware.coffeeapp.ui.components.other.Dividers
import ru.plumsoftware.coffeeapp.ui.components.buttons.PrimaryButton
import ru.plumsoftware.coffeeapp.ui.components.fill_in.DateDefaults
import ru.plumsoftware.coffeeapp.ui.components.fill_in.MaskVisualTransformation
import ru.plumsoftware.coffeeapp.ui.components.fill_in.TextField
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(
    nameViewModel: NameViewModel,
    onEvent: (NameViewModel.Event) -> Unit
) {

    val state = nameViewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        nameViewModel.label.asSharedFlow().collect { label ->
            when (label) {
                NameViewModel.Label.ShowBottomSheetDialog -> {
                    state.sheetState.expand()
                }

                NameViewModel.Label.HideBottomSheetDialog -> {
                    state.sheetState.hide()
                }
            }
        }
    }

    Scaffold {
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
                        onEvent(NameViewModel.Event.ChangeName(name = it))
                    }
                )
                TextField(
                    text = state.birthday,
                    label = stringResource(id = R.string.age_placeholder),
                    mask = MaskVisualTransformation(DateDefaults.DATE_MASK),
                    onValueChange = {
                        onEvent(NameViewModel.Event.ChangeBirthday(birthday = it))
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = Padding.Items.extraSmallScreenPadding,
                        alignment = Alignment.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Checkbox(
                        checked = state.checkBox,
                        onCheckedChange = {
                            onEvent(NameViewModel.Event.TogglePrivacyPolicy(checked = it))
                        })
                    Text(
                        text = stringResource(id = R.string.agree_with_hint),
                        style = MaterialTheme.typography.bodySmall
                    )
                    TextButton(
                        onClick = {
                            onEvent(NameViewModel.Event.ShowBottomSheetDialog)
                        },
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = R.string.pp_hint),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Dividers(selected = 1)
                PrimaryButton(onClick = {
                    onEvent(
                        NameViewModel.Event.SaveData(
                            birthday = state.birthday,
                            name = state.name
                        )
                    )
                    nameViewModel.onOutput(NameViewModel.Output.Go)
                }, isActive = state.isActive)
            }

            if (state.showBottomSheet)
                ModalBottomSheet(
                    onDismissRequest = {
                        onEvent(NameViewModel.Event.HideBottomSheetDialog)
                    },
                    sheetState = state.sheetState
                ) {
                    LazyColumn(content = {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(all = Padding.Screens.smallScreenPadding),
                                text = stringResource(id = R.string.privacy_policy),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    })
                }
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
                nameViewModel = NameViewModel(
                    sharedPreferencesStorage = null,
                    {}
                ),
                onEvent = {}
            )
        }
    }
}