package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun Dividers(selected: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = Padding.Items.smallScreenPadding,
            alignment = Alignment.CenterHorizontally
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = Padding.Items.largeScreenPadding)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        Color.Transparent
                    ),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset.Infinite
                )
            )
    ) {
        for (i in 0..2) {
            if (selected == i) {
                Card(
                    modifier = Modifier
                        .width(Size.Divider.width)
                        .height(Size.Divider.height),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = MaterialTheme.shapes.extraSmall,
                    content = {}
                )
            } else {
                Card(
                    modifier = Modifier
                        .width(Size.Divider.width)
                        .height(Size.Divider.height),
                    colors = CardDefaults.cardColors(
                        containerColor = getExtendedColors().dividerColor
                    ),
                    shape = MaterialTheme.shapes.extraSmall,
                    content = {}
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DividersPreview() {
    CoffeeAppTheme(colors = LightColors) {
        Dividers(selected = 0)
    }
}