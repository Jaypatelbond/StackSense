package com.stacksense.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    data: List<Pair<Color, Float>>,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(data) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
    }

    Canvas(modifier = modifier.size(200.dp)) {
        val total = data.sumOf { it.second.toDouble() }.toFloat()
        if (total == 0f) return@Canvas

        var startAngle = -90f
        val sizeMin = size.minDimension
        val chartSize = Size(sizeMin, sizeMin)

        data.forEach { (color, value) ->
            val sweepAngle = (value / total) * 360f * animatedProgress.value
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = chartSize
            )
            startAngle += sweepAngle
        }
    }
}
