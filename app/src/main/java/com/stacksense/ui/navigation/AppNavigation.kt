package com.stacksense.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
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

object Routes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val DETAIL = "detail/{packageName}"
    
    const val HOME = "home"
    const val STATS = "stats"

    fun detailRoute(packageName: String): String = "detail/$packageName"
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
    }
}

@Composable
fun MainScreen(
    onAppClick: (String) -> Unit,
    onNavigateToSettings: () -> Unit
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
                }
            }
        }
    }
}
