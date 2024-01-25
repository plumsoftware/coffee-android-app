package ru.plumsoftware.coffeeapp.ui.components.fill_in

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun SearchField(onClick: () -> Unit = {}) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textStyle = MaterialTheme.typography.labelMedium,
        shape = MaterialTheme.shapes.medium,
        value = text,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = LocalContentColor.current.copy(
                        alpha = 0.3f
                    )
                )
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = getExtendedColors().cardBackground,
            unfocusedBorderColor = getExtendedColors().cardBackground,
            focusedContainerColor = getExtendedColors().cardBackground.copy(alpha = 0.7f),
            focusedBorderColor = getExtendedColors().cardBackground.copy(alpha = 0.7f),
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search, contentDescription = stringResource(
                    id = R.string.search_content_description
                )
            )
        },
        trailingIcon = {
            if (text.isNotEmpty())
                IconButton(
                    onClick = { text = "" }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close_content_description)
                    )
                }
        },
        onValueChange = {
            text = it
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onClick()
            }
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchFieldPreview() {
    CoffeeAppTheme(useDarkTheme = true) {
        SearchField()
    }
}