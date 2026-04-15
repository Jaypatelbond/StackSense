package com.stacksense.ui.screens.export

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.export.BatchExportManager
import com.stacksense.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val batchExportManager: BatchExportManager
) : ViewModel() {

    private val _exportStatus = MutableStateFlow<String?>(null)
    val exportStatus: StateFlow<String?> = _exportStatus

    fun performExport(format: String, outputDir: File) {
        viewModelScope.launch {
            _exportStatus.value = "Exporting..."
            val apps = appRepository.getInstalledAppsWithCache()
            val file = batchExportManager.exportBatch(apps, format, outputDir)
            if (file != null) {
                _exportStatus.value = "Success: Exported to ${file.name}"
            } else {
                _exportStatus.value = "Failed to export"
            }
        }
    }
}
