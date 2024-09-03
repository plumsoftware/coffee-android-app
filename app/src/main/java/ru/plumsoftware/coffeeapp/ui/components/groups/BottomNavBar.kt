package ru.plumsoftware.coffeeapp.ui.components.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.dto.BottomBarDto
import ru.plumsoftware.coffeeapp.ui.screens.Screens
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun BottomNavBar(
    onClick: (String) -> Unit,
    selected: Int = 0
) {

    val selected = remember {
        mutableIntStateOf(selected)
    }

    val list = listOf<BottomBarDto>(
        BottomBarDto(
            name = stringResource(id = R.string.home),
            screens = Screens.HOME,
            painter = painterResource(id = R.drawable.coffee_cup_icon)
        ),
        BottomBarDto(
            name = stringResource(id = R.string.liked),
            screens = Screens.LIKED,
            painter = painterResource(id = R.drawable.liked)
        ),
        BottomBarDto(
            name = stringResource(id = R.string.settings),
            screens = Screens.SETTINGS,
            painter = painterResource(id = R.drawable.profile)
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        list.forEachIndexed { index, item ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1.0f),
                contentPadding = PaddingValues(
                    vertical = Padding.Items.smallScreenPadding,
                    horizontal = Padding.Items.largeScreenPadding
                ),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (selected.intValue == index) MaterialTheme.colorScheme.onPrimaryContainer else getExtendedColors().bottomIconColor
                ),
                onClick = {
                    selected.intValue = index
                    onClick(item.screens)
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = Padding.Items.smallScreenPadding,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painter = item.painter,
                        contentDescription = item.name,
                        tint = if (selected.intValue == index) MaterialTheme.colorScheme.primary else getExtendedColors().bottomIconColor
                    )

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelSmall.copy(color = if (selected.intValue == index) MaterialTheme.colorScheme.primary else getExtendedColors().bottomIconColor),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BottomNavBarPreview() {
    CoffeeAppTheme {
        BottomNavBar(
            onClick = { it ->

            }
        )
    }
}