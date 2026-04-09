package com.stacksense.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VennDiagram(
    sharedCount: Int,
    uniqueApp1Count: Int,
    uniqueApp2Count: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(150.dp)) {
        val radius = size.minDimension / 4
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawCircle(
            color = Color(0xFF2196F3).copy(alpha = 0.5f),
            radius = radius,
            center = androidx.compose.ui.geometry.Offset(centerX - radius/1.5f, centerY)
        )
        drawCircle(
            color = Color(0xFFE91E63).copy(alpha = 0.5f),
            radius = radius,
            center = androidx.compose.ui.geometry.Offset(centerX + radius/1.5f, centerY)
        )
    }
}
