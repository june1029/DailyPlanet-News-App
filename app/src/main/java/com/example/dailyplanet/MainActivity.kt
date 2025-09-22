package com.example.dailyplanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dailyplanet.ui.composables.AboutDialog
import com.example.dailyplanet.ui.composables.FavoritesScreen
import com.example.dailyplanet.ui.composables.NewsScreen
import com.example.dailyplanet.ui.composables.SearchScreen
import com.example.dailyplanet.ui.theme.DailyPlanetTheme
import com.example.dailyplanet.ui.viewmodel.NewsViewModel

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val newsViewModel by viewModels<NewsViewModel>()

        setContent {
            DailyPlanetTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                var showAboutDialog by remember { mutableStateOf(false) }

                if (showAboutDialog) {
                    AboutDialog(onDismiss = { showAboutDialog = false })
                }

                val items = listOf(
                    BottomNavItem("Home", Icons.Default.Home, "home"),
                    BottomNavItem("Search", Icons.Default.Search, "search"),
                    BottomNavItem("Favorites", Icons.Default.Favorite, "favorites"),
                    BottomNavItem("About", Icons.Default.Info, "about")
                )

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 0.dp
                        ) {
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(screen.label) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true && screen.route != "about",
                                    onClick = {
                                        if (screen.route == "about") {
                                            showAboutDialog = true
                                        } else {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                                        indicatorColor = MaterialTheme.colorScheme.surface,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { NewsScreen(viewModel = newsViewModel) }
                        composable("search") { SearchScreen(viewModel = newsViewModel) }
                        composable("favorites") { FavoritesScreen(viewModel = newsViewModel) }
                    }
                }
            }
        } // <-- THIS BRACE WAS MISSING
    }
}