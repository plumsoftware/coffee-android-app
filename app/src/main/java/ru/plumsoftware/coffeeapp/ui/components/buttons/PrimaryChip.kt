package ru.plumsoftware.coffeeapp.ui.components.buttons

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryChip(selected: Boolean, text: String, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        shape = MaterialTheme.shapes.medium,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = getExtendedColors().cardBackground,
            labelColor = MaterialTheme.colorScheme.outline,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Color.Transparent,
            borderWidth = 0.dp,
            selectedBorderColor = Color.Transparent,
            selectedBorderWidth = 0.dp
        ),
        onClick = onClick,
        label = {
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun PrimaryChipPreview() {
    CoffeeAppTheme {
        PrimaryChip(
            selected = false,
            text = "Капучино",
            onClick = {}
        )
    }
}