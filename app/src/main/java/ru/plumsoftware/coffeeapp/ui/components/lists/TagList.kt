package ru.plumsoftware.coffeeapp.ui.components.lists

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffee.R as C
import ru.plumsoftware.coffeeapp.ui.components.buttons.PrimaryChip
import ru.plumsoftware.coffeeapp.ui.theme.CoffeeAppTheme
import ru.plumsoftware.coffeeapp.ui.theme.Padding

@Composable
fun TagList(onClick: (Int, String) -> Unit, tagArray: Array<String>) {
    var selectedTagIndex by remember {
        mutableIntStateOf(0)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            itemsIndexed(tagArray) { index, item ->
                PrimaryChip(
                    selected = index == selectedTagIndex,
                    text = item,
                    onClick = {
                        selectedTagIndex = index
                        onClick(index, item)
                    }
                )
                Spacer(modifier = Modifier.width(width = Padding.Items.mediumScreenPadding))
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun TagListPreview() {
    CoffeeAppTheme(useDarkTheme = true) {
        Surface(contentColor = MaterialTheme.colorScheme.background) {
            val tagArray = stringArrayResource(id = C.array.tag_list)
            TagList(
                tagArray = tagArray,
                onClick = { _, _ ->

                }
            )
        }
    }
}