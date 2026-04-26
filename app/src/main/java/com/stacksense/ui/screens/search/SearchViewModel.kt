package com.stacksense.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.*
import com.stacksense.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(SearchFilter())
    val filter: StateFlow<SearchFilter> = _filter

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<AppInfo>> = _filter
        .debounce(250)
        .flatMapLatest { currentFilter ->
            appRepository.getInstalledAppsFlow(true).map { apps ->
                apps.filter { app ->
                    if (currentFilter.query.isNotEmpty() && !app.appName.contains(currentFilter.query, ignoreCase = true) && !app.packageName.contains(currentFilter.query, ignoreCase = true)) {
                        return@filter false
                    }
                    if (currentFilter.language != null && !app.languages.contains(currentFilter.language)) {
                        return@filter false
                    }
                    if (currentFilter.category != null && app.libraries.none { it.category == currentFilter.category }) {
                        return@filter false
                    }
                    if (currentFilter.isSystemApp != null && app.isSystemApp != currentFilter.isSystemApp) {
                        return@filter false
                    }
                    if (currentFilter.isDebuggable != null && app.isDebuggable != currentFilter.isDebuggable) {
                        return@filter false
                    }
                    if (currentFilter.minSdk > 0 && app.minSdkVersion < currentFilter.minSdk) {
                        return@filter false
                    }
                    if (currentFilter.targetSdk > 0 && app.targetSdkVersion < currentFilter.targetSdk) {
                        return@filter false
                    }
                    true
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateQuery(query: String) {
        _filter.value = _filter.value.copy(query = query)
    }

    fun updateLanguage(lang: Language?) {
        _filter.value = _filter.value.copy(language = lang)
    }

    fun updateCategory(cat: LibraryCategory?) {
        _filter.value = _filter.value.copy(category = cat)
    }

    fun updateSystemApp(sys: Boolean?) {
        _filter.value = _filter.value.copy(isSystemApp = sys)
    }

    fun updateDebuggable(debug: Boolean?) {
        _filter.value = _filter.value.copy(isDebuggable = debug)
    }
}
