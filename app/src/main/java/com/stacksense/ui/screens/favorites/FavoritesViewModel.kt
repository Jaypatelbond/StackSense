package com.stacksense.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.AppInfo
import com.stacksense.data.repository.AppRepository
import com.stacksense.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val appRepository: AppRepository
) : ViewModel() {

    val favorites: StateFlow<List<AppInfo>> = combine(
        appRepository.getInstalledAppsFlow(true),
        favoritesRepository.getFavorites()
    ) { apps, favs ->
        apps.filter { it.packageName in favs }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(packageName: String) {
        viewModelScope.launch {
            val favs = favoritesRepository.getFavorites().first()
            if (packageName in favs) {
                favoritesRepository.removeFavorite(packageName)
            } else {
                favoritesRepository.addFavorite(packageName)
            }
        }
    }
}
