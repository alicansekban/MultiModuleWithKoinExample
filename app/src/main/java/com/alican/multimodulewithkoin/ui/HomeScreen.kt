package com.alican.multimodulewithkoin.ui

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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
    Box(modifier = Modifier.fillMaxSize()) {
//        AnimatedVisibility(firstVisibleItem) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .background(
//                        Color.Red
//                    )
//            ) {
//                Spacer(Modifier.width(20.dp))
//                Icon(
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = "",
//                    modifier = Modifier.size(32.dp)
//                )
//                Column {
//                    Text("Alican Sekban")
//                    Text("123456789")
//                }
//            }
//        }
//        AnimatedVisibility(firstVisibleItem.not()) {
//
//        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (firstVisibleItem) 150.dp else 250.dp) // Dynamic height
                .background(Color.Red)
                .animateContentSize() // Smooth transitions
        ) {
            Spacer(Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text("Alican Sekban")
                Text("123456789")
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = if (firstVisibleItem) 150.dp else 180.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = listState
        ) {
            item {
                DoubleTopHorizontal()
            }
            item {
                HomeScreenFilterComponent()
            }
            items(10) {
                HomeCampaignComponent()
            }
            item {
                Spacer(Modifier.height(150.dp))
            }
        }
    }
}

@Composable
fun CollapsingToolbarWithOffset() {
    val listState = rememberLazyListState()
    val maxHeight = 250.dp
    val minHeight = 100.dp

    // Calculate the offset based on scroll position
    val offsetHeightPx = with(LocalDensity.current) {
        (maxHeight - minHeight).toPx()
    }

    val toolbarOffset = remember {
        derivedStateOf {
            val fraction = listState.firstVisibleItemScrollOffset / offsetHeightPx
            (1 - fraction.coerceIn(0f, 1f)) * offsetHeightPx
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Collapsing Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight)
                .graphicsLayer {
                    translationY = -toolbarOffset.value
                }
                .background(Color.Red)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Column {
                    Text("Alican Sekban")
                    Text("123456789")
                }
            }
        }

        // List Content
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = maxHeight)
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
