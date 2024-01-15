package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.DarkColors
import ru.plumsoftware.coffeeapp.ui.theme.LightColors

@Composable
fun RButton(selected: MutableState<Boolean>) {
    RadioButton(
        selected = selected.value,
        onClick = { selected.value = !selected.value },
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun RButtonPreview() {
    CoffeeAppTheme() {
        RButton(selected = remember { mutableStateOf(true) })
    }
}