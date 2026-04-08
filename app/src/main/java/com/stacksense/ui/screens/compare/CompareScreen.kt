package com.stacksense.ui.screens.compare

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stacksense.ui.components.VennDiagram
import com.stacksense.ui.components.ComparisonCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
    packageNames: List<String>,
    onNavigateBack: () -> Unit,
    viewModel: CompareViewModel = hiltViewModel()
) {
    LaunchedEffect(packageNames) {
        viewModel.compareApps(packageNames)
    }

    val result by viewModel.comparisonResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Apps") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val res = result
            if (res == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Comparing: ${res.apps.joinToString(", ") { it.appName }}", style = MaterialTheme.typography.titleMedium)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        res.apps.forEach { app ->
                            ComparisonCard(appInfo = app, modifier = Modifier.weight(1f))
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Common Languages", style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            if (res.commonLanguages.isNotEmpty()) {
                                Text(res.commonLanguages.joinToString { it.displayName })
                            } else {
                                Text("None")
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Common Libraries", style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            if (res.commonLibraries.isNotEmpty()) {
                                Text(res.commonLibraries.joinToString { it.name })
                            } else {
                                Text("None")
                            }
                        }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Common Permissions", style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            if (res.commonPermissions.isNotEmpty()) {
                                Text(res.commonPermissions.joinToString())
                            } else {
                                Text("None")
                            }
                        }
                    }
                }
            }
        }
    }
}
