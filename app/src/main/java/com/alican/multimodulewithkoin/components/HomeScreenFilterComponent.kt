package com.alican.multimodulewithkoin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alican.multimodulewithkoin.R

@Composable
fun HomeScreenFilterComponent() {
    val items = listOf(
        FilterModel(
            name = "Tümü (4)",
            isSelected = true,
        ),
        FilterModel(
            name = "Çekilişler (2)",
            isSelected = false,
            icon = ImageVector.vectorResource(R.drawable.ic_launcher_background)
        ),
        FilterModel(
            name = "Kazandıran fırsatlar (2)",
            isSelected = false,
            icon = ImageVector.vectorResource(R.drawable.ic_launcher_background)
        )
    )
    var list by remember {
        mutableStateOf(items)
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(list) { filter ->
            FilterComponentItem(filter, onclick = {
                val updatedItems = list.map {
                    if (filter == it) it.copy(isSelected = true) else it.copy(isSelected = false)
                }
                list = updatedItems
            })
        }
    }
}

@Composable
fun FilterComponentItem(item: FilterModel, onclick: () -> Unit = {}) {
    val backgroundColor = if (item.isSelected) Color.Black else Color.Gray
    val textColor = if (item.isSelected) Color.White else Color.Black
    Card(
        modifier = Modifier.clickable {
            onclick.invoke()
        },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.icon?.let {
                Icon(imageVector = it, contentDescription = "", modifier = Modifier.size(18.dp))
            }
            Text(text = item.name, color = textColor)

        }
    }
}

data class FilterModel(
    val name: String,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null
)


@Preview
@Composable
private fun ComponentsPreview() {
    val list = listOf(
        FilterModel(
            name = "Tümü (4)",
            isSelected = true,
        ),
        FilterModel(
            name = "Tümü (4)",
            isSelected = true,
            icon = ImageVector.vectorResource(R.drawable.ic_launcher_background)
        ),
        FilterModel(
            name = "Tümü (4)",
            isSelected = true,
            icon = ImageVector.vectorResource(R.drawable.ic_launcher_background)
        )
    )

    HomeScreenFilterComponent()
}