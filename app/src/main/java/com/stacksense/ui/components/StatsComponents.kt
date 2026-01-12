package com.stacksense.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stacksense.data.model.Language
import com.stacksense.data.model.LibraryCategory
import kotlin.math.min

/**
 * Animated donut chart showing language/framework distribution.
 */
@Composable
fun AnimatedDonutChart(
    data: Map<Language, Int>,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }
    
    LaunchedEffect(data) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }
    
    val total = data.values.sum().toFloat()
    if (total == 0f) return
    
    Canvas(modifier = modifier) {
        val strokeWidth = 24.dp.toPx()
        val radius = (min(size.width, size.height) - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)
        
        var startAngle = -90f
        
        data.forEach { (language, count) ->
            val sweepAngle = (count / total) * 360f * animatedProgress.value
            
            drawArc(
                color = Color(language.color),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            startAngle += (count / total) * 360f * animatedProgress.value
        }
    }
}

/**
 * Stats card showing app analysis summary with animations.
 */
@Composable
fun StatsCard(
    totalApps: Int,
    analyzedApps: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (totalApps > 0) analyzedApps.toFloat() / totalApps else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "progress"
    )
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                value = totalApps,
                label = "Total Apps",
                color = MaterialTheme.colorScheme.primary
            )
            StatItem(
                value = analyzedApps,
                label = "Analyzed",
                color = MaterialTheme.colorScheme.tertiary
            )
            StatItem(
                value = ((animatedProgress * 100).toInt()),
                label = "Progress",
                suffix = "%",
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun StatItem(
    value: Int,
    label: String,
    color: Color,
    suffix: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$value$suffix",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Animated horizontal bar chart for library categories.
 */
@Composable
fun LibraryCategoryBars(
    data: Map<LibraryCategory, Int>,
    modifier: Modifier = Modifier
) {
    val maxCount = data.values.maxOrNull() ?: 1
    
    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        data.entries.sortedByDescending { it.value }.take(6).forEach { (category, count) ->
            val targetProgress = count.toFloat() / maxCount
            val animatedProgress by animateFloatAsState(
                targetValue = targetProgress,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "bar_${category.name}"
            )
            
            CategoryBarItem(
                category = category,
                count = count,
                progress = animatedProgress
            )
        }
    }
}

@Composable
private fun CategoryBarItem(
    category: LibraryCategory,
    count: Int,
    progress: Float
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = Color(category.color)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            // Background
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {}
            // Foreground bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp),
                color = Color(category.color).copy(alpha = 0.8f)
            ) {}
        }
    }
}

/**
 * Language distribution legend.
 */
@Composable
fun LanguageLegend(
    languages: Map<Language, Int>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        languages.entries.sortedByDescending { it.value }.forEach { (language, count) ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(12.dp),
                    shape = RoundedCornerShape(3.dp),
                    color = Color(language.color)
                ) {}
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = language.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Animated counter that counts up to the target value.
 */
@Composable
fun AnimatedCounter(
    targetValue: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateFloatAsState(
        targetValue = targetValue.toFloat(),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "counter"
    )
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = animatedValue.toInt().toString(),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
