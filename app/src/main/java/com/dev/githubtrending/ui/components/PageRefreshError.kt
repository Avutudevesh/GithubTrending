package com.dev.githubtrending.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PageRefreshError(error: Throwable?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Error: ${error?.localizedMessage.orEmpty()}", modifier = Modifier.align(Alignment.Center))
    }
}