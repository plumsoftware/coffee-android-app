package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun PrimaryButton(isActive: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isActive,
        contentPadding = PaddingValues(
            horizontal = Padding.ButtonP.horizontal,
            vertical = Padding.ButtonP.vertical
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = stringResource(id = R.string.primary_button_text),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PrimaryButtonPreview() {
    CoffeeAppTheme() {
        PrimaryButton(onClick = {})
    }
}