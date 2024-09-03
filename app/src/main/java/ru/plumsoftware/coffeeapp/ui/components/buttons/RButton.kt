package ru.plumsoftware.coffeeapp.ui.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme

@Composable
fun RButton(selected: Boolean, onClick: () -> Unit) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun RButtonPreview() {
    CoffeeAppTheme() {
        RButton(selected = true, onClick = {

        })
    }
}