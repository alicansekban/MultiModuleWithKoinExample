package com.alican.multimodulewithkoin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun DoubleTopHorizontal(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun DoubleTopVertical(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun TripleTop(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray)
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray)
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Preview
@Composable
private fun ComponentsPreview() {
    TripleTop()

}