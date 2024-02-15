package ru.plumsoftware.coffeeapp.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.Size
import ru.plumsoftware.coffeeapp.ui.theme.getExtendedColors

@Composable
fun LikeButton(isCoffeeLiked: Int, onLikeClick: () -> Unit) {
    val isLiked = remember {
        mutableIntStateOf(isCoffeeLiked)
    }

    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent
        ),
        onClick =
        {
            if (isLiked.intValue == 1) isLiked.intValue =
                0 else isLiked.intValue = 1

            onLikeClick()
        }
    ) {
        Icon(
            painter = if (isLiked.intValue == 1) painterResource(id = R.drawable.liked_drink) else painterResource(
                id = R.drawable.liked
            ),
            contentDescription = stringResource(
                id = R.string.coffee_like_content_description
            ),
            tint = if (isLiked.intValue == 1) getExtendedColors().likeColor else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(size = Size.Coffee.likeSize)
                .clip(MaterialTheme.shapes.medium),
        )
    }
}