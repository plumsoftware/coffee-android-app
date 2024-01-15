package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun TextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.Items.extraSmallScreenPadding,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
        )
        OutlinedTextField(
            value = text,
            textStyle = MaterialTheme.typography.labelMedium,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground,
                unfocusedBorderColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground,
                focusedContainerColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground.copy(alpha = 0.7f),
                focusedBorderColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground.copy(alpha = 0.7f),
            ),
            onValueChange = onValueChange,
            leadingIcon = leadingIcon
        )
    }
}

@Composable
@Preview
private fun TextFieldPreview() {
    CoffeeAppTheme () {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TextField(
                text = "",
                label = stringResource(id = R.string.name_placeholder),
                onValueChange = {},
                leadingIcon = {}
            )
        }
    }
}