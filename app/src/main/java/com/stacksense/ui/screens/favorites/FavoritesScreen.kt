package com.stacksense.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stacksense.ui.components.AppCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onAppClick: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesList by viewModel.favorites.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Favorite Apps") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (favoritesList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No favorites added yet.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoritesList) { app ->
                        AppCard(appInfo = app, onClick = onAppClick)
                    }
                }
            }
        }
    }
}
