package com.alican.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = koinViewModel(),
    onItemClick: () -> Unit
) {

    val items by viewModel.exercises.collectAsStateWithLifecycle(emptyList())
    Column(modifier = modifier.fillMaxSize()) {

        items.forEach {
            Text(text = it, modifier = Modifier.clickable { onItemClick.invoke() })
        }
    }
}