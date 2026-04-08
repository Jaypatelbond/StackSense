package com.stacksense.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stacksense.data.model.AppInfo

@Composable
fun ComparisonCard(
    appInfo: AppInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(appInfo.appName, style = MaterialTheme.typography.titleMedium)
            Text(appInfo.packageName, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Version: ${appInfo.versionName}", style = MaterialTheme.typography.bodyMedium)
            Text("SDK: Min ${appInfo.minSdkVersion} / Target ${appInfo.targetSdkVersion}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
