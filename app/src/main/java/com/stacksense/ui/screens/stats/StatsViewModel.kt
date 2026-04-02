package com.stacksense.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stacksense.data.model.OverallStats
import com.stacksense.data.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val statsRepository: StatsRepository
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> = statsRepository.getOverallStats()
        .map { StatsUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StatsUiState.Loading
        )
}

sealed interface StatsUiState {
    data object Loading : StatsUiState
    data class Success(val stats: OverallStats) : StatsUiState
    data class Error(val message: String) : StatsUiState
}
