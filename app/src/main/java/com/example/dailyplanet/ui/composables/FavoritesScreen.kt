package com.example.dailyplanet.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyplanet.ui.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(viewModel: NewsViewModel) {
    val savedArticles by viewModel.savedNews.collectAsStateWithLifecycle()

    // The Scaffold is removed, and Column is now the root element.
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("\uD835\uDD23\uD835\uDD1E\uD835\uDD33\uD835\uDD2C\uD835\uDD32\uD835\uDD2F\uD835\uDD26\uD835\uDD31\uD835\uDD22 \uD835\uDD1E\uD835\uDD2F\uD835\uDD31\uD835\uDD26\uD835\uDD20\uD835\uDD29\uD835\uDD22\uD835\uDD30. ❤\uFE0F", style = MaterialTheme.typography.headlineLarge) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            windowInsets = WindowInsets(0.dp)
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (savedArticles.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "No favorites yet",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "You have no favorite articles yet.",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = savedArticles,
                        key = { article -> article.url }
                    ) { article ->
                        NewsCard(
                            article = article,
                            viewModel = viewModel,
                            isFavorite = true
                        )
                    }
                }
            }
        }
    }
}