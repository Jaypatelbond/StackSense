package com.stacksense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stacksense.data.repository.DarkThemeConfig
import com.stacksense.ui.navigation.AppNavigation
import com.stacksense.ui.theme.StackSenseTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point Activity for StackSense.
 * Uses Jetpack Compose for the UI with Material 3 theming.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before calling super.onCreate()
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()

        setContent {
            val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

            val darkTheme = when (userPreferences?.darkThemeConfig) {
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                null -> isSystemInDarkTheme() // Default while loading
            }
            
            val dynamicColor = userPreferences?.useDynamicColors ?: true

            StackSenseTheme(
                darkTheme = darkTheme,
                dynamicColor = dynamicColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
