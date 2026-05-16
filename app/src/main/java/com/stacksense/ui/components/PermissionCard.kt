package com.stacksense.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stacksense.data.model.PermissionDetail

@Composable
fun PermissionCard(
    detail: PermissionDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(detail.permissionName.substringAfterLast("."), style = MaterialTheme.typography.bodyLarge)
                RiskBadge(riskLevel = detail.riskLevel)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(detail.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
