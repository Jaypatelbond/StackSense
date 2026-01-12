package com.stacksense.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stacksense.ui.screens.detail.DetailScreen
import com.stacksense.ui.screens.home.HomeScreen
import com.stacksense.ui.screens.settings.SettingsScreen

/**
 * Navigation routes for the app.
 */
object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val DETAIL = "detail/{packageName}"

    fun detailRoute(packageName: String): String {
        return "detail/$packageName"
    }
}

/**
 * Main navigation graph for StackSense.
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Home Screen
        composable(
            route = Routes.HOME,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeOut()
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeIn()
            }
        ) {
            HomeScreen(
                onAppClick = { packageName ->
                    navController.navigate(Routes.detailRoute(packageName))
                },
                onNavigateToSettings = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        // Detail Screen
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("packageName") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeIn()
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeOut()
            }
        ) {
            DetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Settings Screen
        composable(
            route = Routes.SETTINGS,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeIn()
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                ) + fadeOut()
            }
        ) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
