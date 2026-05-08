package com.stacksense.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.stacksense.ui.screens.detail.DetailScreen
import com.stacksense.ui.screens.home.HomeScreen
import com.stacksense.ui.screens.settings.SettingsScreen
import com.stacksense.ui.screens.stats.StatsScreen
import com.stacksense.ui.screens.favorites.FavoritesScreen
import com.stacksense.ui.screens.compare.CompareScreen
import com.stacksense.ui.screens.export.ExportScreen
import com.stacksense.ui.screens.search.AdvancedSearchScreen
import com.stacksense.ui.screens.onboarding.OnboardingScreen

object Routes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val DETAIL = "detail/{packageName}"
    const val COMPARE = "compare/{packageNames}"
    const val EXPORT = "export"
    const val SEARCH = "search"
    const val ONBOARDING = "onboarding"
    
    const val HOME = "home"
    const val STATS = "stats"
    const val FAVORITES = "favorites"

    fun detailRoute(packageName: String): String = "detail/$packageName"
    fun compareRoute(packageNames: List<String>): String = "compare/${packageNames.joinToString(",")}"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(route = Routes.MAIN) {
            MainScreen(
                onAppClick = { packageName ->
                    navController.navigate(Routes.detailRoute(packageName))
                },
                onNavigateToSettings = {
                    navController.navigate(Routes.SETTINGS)
                },
                onNavigateToCompare = { packageNames ->
                    navController.navigate(Routes.compareRoute(packageNames))
                },
                onNavigateToExport = {
                    navController.navigate(Routes.EXPORT)
                },
                onNavigateToSearch = {
                    navController.navigate(Routes.SEARCH)
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("packageName") { type = NavType.StringType })
        ) {
            DetailScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = Routes.SETTINGS) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.COMPARE,
            arguments = listOf(navArgument("packageNames") { type = NavType.StringType })
        ) { backStackEntry ->
            val pkgs = backStackEntry.arguments?.getString("packageNames")?.split(",") ?: emptyList()
            CompareScreen(
                packageNames = pkgs,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.EXPORT) {
            ExportScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = Routes.SEARCH) {
            AdvancedSearchScreen(
                onAppClick = { packageName ->
                    navController.navigate(Routes.detailRoute(packageName))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.ONBOARDING) {
            OnboardingScreen(onFinish = { navController.popBackStack() })
        }
    }
}

@Composable
fun MainScreen(
    onAppClick: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCompare: (List<String>) -> Unit = {},
    onNavigateToExport: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Stats") },
                    label = { Text("Stats") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Crossfade(targetState = selectedTab, label = "tabTransition") { tab ->
                when (tab) {
                    0 -> HomeScreen(
                        onAppClick = onAppClick,
                        onNavigateToSettings = onNavigateToSettings
                    )
                    1 -> StatsScreen()
                    2 -> FavoritesScreen(onAppClick = onAppClick)
                }
            }
        }
    }
}
