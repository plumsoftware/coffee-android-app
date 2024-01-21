package ru.plumsoftware.coffeeapp.ui.components.fill_in

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AgeHint(age: String) {
    Text(
        text = age,
        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)
    )
}
