package com.stacksense.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class DarkThemeConfig {
    FOLLOW_SYSTEM, LIGHT, DARK
}

data class UserPreferences(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColors: Boolean,
    val showSystemApps: Boolean
)

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val DARK_THEME_CONFIG = stringPreferencesKey("dark_theme_config")
        val USE_DYNAMIC_COLORS = booleanPreferencesKey("use_dynamic_colors")
        val SHOW_SYSTEM_APPS = booleanPreferencesKey("show_system_apps")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val darkThemeConfigString = preferences[PreferencesKeys.DARK_THEME_CONFIG]
                ?: DarkThemeConfig.FOLLOW_SYSTEM.name
            val darkThemeConfig = try {
                DarkThemeConfig.valueOf(darkThemeConfigString)
            } catch (e: IllegalArgumentException) {
                DarkThemeConfig.FOLLOW_SYSTEM
            }

            val useDynamicColors = preferences[PreferencesKeys.USE_DYNAMIC_COLORS] ?: true
            val showSystemApps = preferences[PreferencesKeys.SHOW_SYSTEM_APPS] ?: false

            UserPreferences(darkThemeConfig, useDynamicColors, showSystemApps)
        }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME_CONFIG] = darkThemeConfig.name
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USE_DYNAMIC_COLORS] = useDynamicColor
        }
    }

    suspend fun setShowSystemApps(showSystemApps: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_SYSTEM_APPS] = showSystemApps
        }
    }
}
