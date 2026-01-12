package com.stacksense.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.AppInfo
import com.stacksense.data.model.Language
import com.stacksense.data.model.ScanProgress
import com.stacksense.data.repository.AppRepository
import com.stacksense.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the home screen.
 */
data class HomeUiState(
    val apps: List<AppInfo> = emptyList(),
    val filteredApps: List<AppInfo> = emptyList(),
    val isLoading: Boolean = true,
    val isScanning: Boolean = false,
    val scanProgress: ScanProgress? = null,
    val searchQuery: String = "",
    val selectedFilters: Set<FilterType> = setOf(FilterType.ALL),
    val includeSystemApps: Boolean = false,
    val error: String? = null,
    val cachedCount: Int = 0,  // Number of apps loaded from cache
    val newScanCount: Int = 0  // Number of apps freshly scanned
)

/**
 * Filter types for the app list.
 */
enum class FilterType(val displayName: String) {
    ALL("All"),
    KOTLIN("Kotlin"),
    JAVA("Java"),
    NATIVE("Native"),
    FLUTTER("Flutter"),
    REACT_NATIVE("React Native"),
    CROSS_PLATFORM("Cross-Platform")
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        // Observe user preferences and switch upstream flow accordingly
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow
                .collect { prefs ->
                    // Update state with new preference
                    _uiState.update { it.copy(includeSystemApps = prefs.showSystemApps) }
                    
                    // Start observing apps depending on system apps setting
                    // Note: We're doing this inside the collect, which effectively restarts observation 
                    // when prefs change. For a cleaner approach we could use flatMapLatest, 
                    // but simple collection cancel/restart works since the outer scope is the same.
                    observeApps(prefs.showSystemApps)
                }
        }
    }
    
    private var appJob: kotlinx.coroutines.Job? = null

    /**
     * Observes the list of installed apps from the repository.
     * Updates automatically when DB changes (e.g., after analysis).
     */
    private fun observeApps(includeSystemApps: Boolean) {
        // Cancel previous observation if any
        appJob?.cancel()
        
        appJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                appRepository.getInstalledAppsFlow(includeSystemApps).collect { apps ->
                    val cachedCount = apps.count { it.isAnalyzed }
                    
                    _uiState.update { state ->
                        state.copy(
                            apps = apps,
                            filteredApps = filterApps(apps, state.searchQuery, state.selectedFilters),
                            isLoading = false,
                            cachedCount = cachedCount
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(isLoading = false, error = "Failed to load apps: ${e.message}") 
                }
            }
        }
    }



    /**
     * Smart scan - only analyzes apps that have changed or are new.
     * Uses cached results for unchanged apps (very fast).
     */
    fun startSmartScan() {
        if (_uiState.value.isScanning) return

        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, error = null, newScanCount = 0) }

            val analyzedApps = mutableListOf<AppInfo>()
            var newScans = 0

            try {
                appRepository.scanAppsOptimized(_uiState.value.includeSystemApps).collect { (progress, app) ->
                    _uiState.update { it.copy(scanProgress = progress) }

                    if (app != null) {
                        analyzedApps.add(app)
                        // Count if this was a fresh scan (not from cache)
                        if (!_uiState.value.apps.any { 
                            it.packageName == app.packageName && it.isAnalyzed 
                        }) {
                            newScans++
                        }
                        
                        _uiState.update { state ->
                            state.copy(
                                apps = analyzedApps.toList(),
                                filteredApps = filterApps(
                                    analyzedApps,
                                    state.searchQuery,
                                    state.selectedFilters
                                ),
                                newScanCount = newScans
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Scan failed: ${e.message}") }
            } finally {
                _uiState.update { 
                    it.copy(
                        isScanning = false, 
                        scanProgress = null,
                        cachedCount = it.apps.count { app -> app.isAnalyzed }
                    ) 
                }
            }
        }
    }

    /**
     * Force re-scan all apps, ignoring cache.
     * Use when you want fresh analysis of everything.
     */
    fun forceRescan() {
        if (_uiState.value.isScanning) return

        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, error = null) }

            val analyzedApps = mutableListOf<AppInfo>()

            try {
                appRepository.forceRescanAllApps(_uiState.value.includeSystemApps).collect { (progress, app) ->
                    _uiState.update { it.copy(scanProgress = progress) }

                    if (app != null) {
                        analyzedApps.add(app)
                        _uiState.update { state ->
                            state.copy(
                                apps = analyzedApps.toList(),
                                filteredApps = filterApps(
                                    analyzedApps,
                                    state.searchQuery,
                                    state.selectedFilters
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Scan failed: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isScanning = false, scanProgress = null) }
            }
        }
    }

    /**
     * Updates the search query and filters apps.
     */
    fun updateSearchQuery(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredApps = filterApps(state.apps, query, state.selectedFilters)
            )
        }
    }

    /**
     * Updates the selected filter.
     * Toggles the filter state:
     * - If "All" is selected, clears others.
     * - If another is selected, "All" is unselected.
     * - If all specific filters are unselected, defaults back to "All".
     */
    fun updateFilter(filter: FilterType) {
        _uiState.update { state ->
            val newFilters = state.selectedFilters.toMutableSet()
            
            if (filter == FilterType.ALL) {
                newFilters.clear()
                newFilters.add(FilterType.ALL)
            } else {
                newFilters.remove(FilterType.ALL)
                if (newFilters.contains(filter)) {
                    newFilters.remove(filter)
                } else {
                    newFilters.add(filter)
                }
                
                // If nothing selected, default to All
                if (newFilters.isEmpty()) {
                    newFilters.add(FilterType.ALL)
                }
            }

            state.copy(
                selectedFilters = newFilters,
                filteredApps = filterApps(state.apps, state.searchQuery, newFilters)
            )
        }
    }

    /**
     * Toggles system app visibility.
     */
    fun toggleSystemApps() {
        val currentValue = _uiState.value.includeSystemApps
        viewModelScope.launch {
            userPreferencesRepository.setShowSystemApps(!currentValue)
        }
    }

    /**
     * Clears all cached scan data.
     */
    fun clearCache() {
        viewModelScope.launch {
            appRepository.clearCache()
        }
    }

    /**
     * Filters apps based on search query and filter types.
     */
    private fun filterApps(
        apps: List<AppInfo>,
        query: String,
        filters: Set<FilterType>
    ): List<AppInfo> {
        return apps.filter { app ->
            val matchesQuery = query.isEmpty() ||
                    app.appName.contains(query, ignoreCase = true) ||
                    app.packageName.contains(query, ignoreCase = true)

            val matchesFilter = if (filters.contains(FilterType.ALL)) {
                true
            } else {
                filters.any { filter ->
                    when (filter) {
                        FilterType.ALL -> true // Should not happen given if check above
                        FilterType.KOTLIN -> app.languages.contains(Language.KOTLIN)
                        FilterType.JAVA -> app.languages.contains(Language.JAVA)
                        FilterType.NATIVE -> app.languages.contains(Language.NATIVE)
                        FilterType.FLUTTER -> app.languages.contains(Language.FLUTTER)
                        FilterType.REACT_NATIVE -> app.languages.contains(Language.REACT_NATIVE)
                        FilterType.CROSS_PLATFORM -> app.languages.any { lang ->
                            lang in listOf(
                                Language.FLUTTER,
                                Language.REACT_NATIVE,
                                Language.XAMARIN,
                                Language.CORDOVA,
                                Language.UNITY,
                                Language.QT,
                                Language.KMP
                            )
                        }
                    }
                }
            }

            matchesQuery && matchesFilter
        }
    }

    /**
     * Clears any error message.
     */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
