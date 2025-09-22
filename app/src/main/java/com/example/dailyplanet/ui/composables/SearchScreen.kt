package com.example.dailyplanet.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyplanet.ui.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: NewsViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchState by viewModel.searchNewsState.collectAsStateWithLifecycle()
    val savedArticles by viewModel.savedNews.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    // The Scaffold is removed, and Column is now the root element.
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "\uD835\uDD30\uD835\uDD22\uD835\uDD1E\uD835\uDD2F\uD835\uDD20\uD835\uDD25 \uD835\uDD2B\uD835\uDD22\uD835\uDD34\uD835\uDD30. \uD83D\uDCF0",
                    // Add this line to use a larger, predefined style from your theme
                    style = MaterialTheme.typography.headlineLarge
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            windowInsets = WindowInsets(0.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search for news...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchNews(searchQuery)
                keyboardController?.hide()
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        if (searchState.articles.isEmpty() && !searchState.isLoading && searchState.error == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search for news",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Search for news about any topic to get started.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            NewsList(
                state = searchState,
                savedArticles = savedArticles,
                viewModel = viewModel,
                onLoadMore = { viewModel.getSearchNewsNextPage() }
            )
        }
    }
}