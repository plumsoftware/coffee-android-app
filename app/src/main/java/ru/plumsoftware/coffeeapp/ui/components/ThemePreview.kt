package ru.plumsoftware.coffeeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.LightColors
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun ThemePreview(colorScheme: ColorScheme) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            Padding.Items.mediumScreenPadding,
            Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .width(Size.AppearancePreview.width)
            .height(Size.AppearancePreview.height)
            .border(
                width = Size.Stroke.smallStrokeSize,
                color = MaterialTheme.colorScheme.scrim,
                shape = MaterialTheme.shapes.small
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.small
            )
            .padding(all = Padding.Screens.extraSmallScreenPadding)
    ) {
        Text(
            text = stringResource(id = R.string.appearance_text_example),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground,
            ),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .height(height = Size.AppearancePreview.cardHeight)
                .fillMaxWidth(),
            content = {}
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = getExtendedColors(background = MaterialTheme.colorScheme.background).cardBackground
            ),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .height(height = Size.AppearancePreview.cardHeight)
                .fillMaxWidth(),
            content = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ThemePreview_() {
    ThemePreview(colorScheme = LightColors)
}