package com.alican.multimodulewithkoin.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alican.multimodulewithkoin.R
import com.alican.multimodulewithkoin.components.DoubleTopHorizontal
import com.alican.multimodulewithkoin.components.FilterModel
import com.alican.multimodulewithkoin.components.FilterType
import com.alican.multimodulewithkoin.components.HomeCampaignComponent
import com.alican.multimodulewithkoin.components.HomeScreenFilterComponent


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val firstVisibleItem by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    val items = listOf(
        FilterModel(
            name = "Tümü (4)",
            isSelected = true,
            filterType = FilterType.ALL
        ),
        FilterModel(
            name = "Çekilişler (2)",
            isSelected = false,
            icon = ImageVector.vectorResource(R.drawable.ic_draw),
            filterType = FilterType.DRAW
        ),
        FilterModel(
            name = "Kazandıran fırsatlar (2)",
            isSelected = false,
            icon = ImageVector.vectorResource(R.drawable.ic_draw_winnning),
            filterType = FilterType.WINNING
        )
    )
    var list by remember {
        mutableStateOf(items)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(firstVisibleItem.not()) {
            Image(
                painter = painterResource(R.drawable.home_top_bg),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // Dynamic height
                .background(Color(0xffE4032C))
                .animateContentSize() // Smooth transitions
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_profile),
                    contentDescription = "",
                    modifier = Modifier.size(58.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        "Orhan Kemal Dağlarca", style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    )
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(Color.White),
                            border = BorderStroke(1.dp, Color.Red),
                            modifier = Modifier.padding(1.dp)
                        ) {
                            Row(
                                Modifier
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Image(
                                    ImageVector.vectorResource(R.drawable.ic_algida_coin),
                                    contentDescription = ""
                                )

                                Text(
                                    "2000", style = TextStyle(
                                        color = Color(0xffE4032C),
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }

            }
            Image(
                ImageVector.vectorResource(R.drawable.ic_notifications),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
        }


        var listCount by remember {
            mutableIntStateOf(4)
        }
        val topPadding = if (firstVisibleItem) 150.dp else 180.dp
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = listState
        ) {
            item {
                DoubleTopHorizontal()
            }
            item {
                HomeScreenFilterComponent(
                    onFilterClick = { model ->
                        listCount = when (model.filterType) {
                            FilterType.ALL -> 4
                            FilterType.DRAW -> 2
                            FilterType.WINNING -> 2

                        }
                        val updatedItems = list.map {
                            if (model == it) it.copy(isSelected = true) else it.copy(isSelected = false)
                        }
                        list = updatedItems
                    },
                    items = list
                )
            }
            items(listCount) {
                HomeCampaignComponent()
            }
            item {
                Spacer(Modifier.height(150.dp))
            }
        }
    }
}
