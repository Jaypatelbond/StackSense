package com.stacksense.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.AppInfo
import com.stacksense.data.model.LibraryCategory
import com.stacksense.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the app detail screen.
 */
data class DetailUiState(
    val appInfo: AppInfo? = null,
    val isLoading: Boolean = true,
    val isAnalyzing: Boolean = false,
    val error: String? = null,
    val isFromCache: Boolean = false
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val exporter: com.stacksense.data.export.AnalysisExporter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val packageName: String = checkNotNull(savedStateHandle["packageName"])

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        loadAppDetails()
    }

    /**
     * Loads app details using cached analysis results when available.
     * This provides instant results for previously scanned apps.
     */
    private fun loadAppDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // This will use cached results if available
                val app = appRepository.getAppByPackage(packageName)

                if (app != null) {
                    _uiState.update { 
                        it.copy(
                            appInfo = app, 
                            isLoading = false,
                            isFromCache = app.isAnalyzed
                        ) 
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "App not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load app: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Re-analyzes the app (force refresh).
     */
    fun reanalyzeApp() {
        val app = _uiState.value.appInfo ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isAnalyzing = true) }

            try {
                val analyzedApp = appRepository.analyzeApp(app)
                _uiState.update {
                    it.copy(
                        appInfo = analyzedApp,
                        isAnalyzing = false,
                        isFromCache = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isAnalyzing = false,
                        error = "Analysis failed: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Gets libraries grouped by category.
     */
    fun getLibrariesByCategory(): Map<LibraryCategory, List<com.stacksense.data.model.LibraryInfo>> {
        return _uiState.value.appInfo?.libraries
            ?.groupBy { it.category }
            ?.toSortedMap(compareBy { it.ordinal })
            ?: emptyMap()
    }

    /**
     * Clears error state.
     */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun exportAnalysis(uri: android.net.Uri) {
        val app = _uiState.value.appInfo ?: return
        viewModelScope.launch {
            try {
                exporter.exportAnalysisToUri(app, uri)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Export failed: ${e.message}") }
            }
        }
    }

    fun saveApk(uri: android.net.Uri) {
        val app = _uiState.value.appInfo ?: return
        viewModelScope.launch {
            try {
                exporter.saveApkToUri(app, uri)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "APK Save failed: ${e.message}") }
            }
        }
    }
}
