package com.stacksense.ui.screens.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.*
import com.stacksense.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompareViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _comparisonResult = MutableStateFlow<ComparisonResult?>(null)
    val comparisonResult: StateFlow<ComparisonResult?> = _comparisonResult.asStateFlow()

    fun compareApps(packageNames: List<String>) {
        viewModelScope.launch {
            val apps = packageNames.mapNotNull { appRepository.getAppByPackage(it) }
            if (apps.size < 2) return@launch

            val commonLanguages = apps.map { it.languages }.reduce { acc, set -> acc.intersect(set) }
            val uniqueLanguages = apps.associate { app ->
                app.packageName to app.languages.subtract(commonLanguages)
            }

            val commonLibNames = apps.map { it.libraries.map { l -> l.name }.toSet() }.reduce { acc, set -> acc.intersect(set) }
            val commonLibraries = apps.first().libraries.filter { it.name in commonLibNames }
            val uniqueLibraries = apps.associate { app ->
                app.packageName to app.libraries.filter { it.name !in commonLibNames }
            }

            val commonPermissions = apps.map { it.permissions.toSet() }.reduce { acc, set -> acc.intersect(set) }.toList()
            val uniquePermissions = apps.associate { app ->
                app.packageName to app.permissions.filter { it !in commonPermissions }
            }

            val sizeComparison = apps.associate { it.packageName to it.apkSize }

            _comparisonResult.value = ComparisonResult(
                apps = apps,
                commonLanguages = commonLanguages,
                uniqueLanguages = uniqueLanguages,
                commonLibraries = commonLibraries,
                uniqueLibraries = uniqueLibraries,
                commonPermissions = commonPermissions,
                uniquePermissions = uniquePermissions,
                sizeComparison = sizeComparison
            )
        }
    }
}
