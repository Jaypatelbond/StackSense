package com.stacksense.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stacksense.ui.components.PieChart
import com.stacksense.ui.components.BarChart
import com.stacksense.ui.components.StatsSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Statistics") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is StatsUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is StatsUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(text = "Error: ${state.message}")
                    }
                }
                is StatsUiState.Success -> {
                    val stats = state.stats
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            StatsSummaryCard(
                                title = "Total Apps",
                                value = stats.totalApps.toString(),
                                colors = listOf(Color(0xFF2196F3), Color(0xFF00BCD4)),
                                modifier = Modifier.weight(1f)
                            )
                            StatsSummaryCard(
                                title = "Total Libraries",
                                value = stats.totalLibraries.toString(),
                                colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63)),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Language Distribution",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (stats.languageDistribution.isNotEmpty()) {
                                    val pieData = stats.languageDistribution.map {
                                        Color(it.language.color) to it.percentage
                                    }
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = androidx.compose.ui.Alignment.Center
                                    ) {
                                        PieChart(data = pieData)
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    stats.languageDistribution.forEach { langStat ->
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                        ) {
                                            Text(langStat.language.displayName)
                                            Text("${String.format("%.1f", langStat.percentage)}% (${langStat.appCount})")
                                        }
                                    }
                                } else {
                                    Text("No data available")
                                }
                            }
                        }

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Library Category Distribution",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (stats.categoryDistribution.isNotEmpty()) {
                                    val barData = stats.categoryDistribution.take(5).map {
                                        it.category.displayName to it.percentage
                                    }
                                    BarChart(data = barData)
                                } else {
                                    Text("No data available")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
