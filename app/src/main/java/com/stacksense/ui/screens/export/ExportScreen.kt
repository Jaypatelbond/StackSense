package com.stacksense.ui.screens.export

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    onNavigateBack: () -> Unit,
    viewModel: ExportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val status by viewModel.exportStatus.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Export Reports") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Select format to export all scanned applications info:")
            
            Button(
                onClick = { viewModel.performExport("csv", context.cacheDir) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export as CSV")
            }

            Button(
                onClick = { viewModel.performExport("html", context.cacheDir) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export as HTML Report")
            }

            status?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(it, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
