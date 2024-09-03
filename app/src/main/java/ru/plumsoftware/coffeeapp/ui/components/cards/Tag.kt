package ru.plumsoftware.coffeeapp.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.Size

@Composable
fun Tag(imageResId: Int, isIntolerable: Boolean, title: String) {
    Card(
        shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(
            containerColor = if (isIntolerable) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = Padding.Items.smallScreenPadding,
                alignment = Alignment.Start
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(all = Padding.Items.smallScreenPadding)
        ) {
            if (imageResId != 0)
                Icon(
                    painter = painterResource(id = imageResId),
                    contentDescription = stringResource(id = R.string.tag_content_description),
                    modifier = Modifier
                        .size(size = Size.Coffee.tagSize),
                    tint = if (isIntolerable) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(color = if (isIntolerable) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}