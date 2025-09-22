package com.example.dailyplanet.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyplanet.R
import com.example.dailyplanet.ui.viewmodel.NewsPagingState
import com.example.dailyplanet.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val savedArticles by viewModel.savedNews.collectAsStateWithLifecycle()
    val categories = viewModel.categories
    val breakingNewsStateMap by viewModel.breakingNewsStateMap.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            viewModel.getBreakingNewsForCategory(categories[pagerState.currentPage])
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // TopAppBar is now the first item in the Column
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.header_image),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(300.dp)
                    )

                }
            },
            actions = {
                IconButton(onClick = { viewModel.refreshCategory(categories[pagerState.currentPage]) }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            windowInsets = WindowInsets(0.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(category) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondBoundsPageCount = 0
        ) { pageIndex ->
            val category = categories[pageIndex]
            val state = breakingNewsStateMap[category] ?: NewsPagingState(isLoading = true)
            NewsList(
                state = state,
                savedArticles = savedArticles,
                viewModel = viewModel,
                onLoadMore = { viewModel.getBreakingNewsNextPage(category) }
            )
        }
    }
}