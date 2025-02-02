package ru.plumsoftware.coffeeapp.ui.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun LoadingDialog() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(all = Padding.Screens.smallScreenPadding)
        )
    }
}