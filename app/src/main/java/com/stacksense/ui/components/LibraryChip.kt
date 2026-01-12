package com.stacksense.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.stacksense.data.model.LibraryCategory
import com.stacksense.data.model.LibraryInfo

/**
 * A chip component for displaying a detected library.
 */
@Composable
fun LibraryChip(
    libraryInfo: LibraryInfo,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = Color(libraryInfo.category.color).copy(alpha = 0.12f)
    val textColor = Color(libraryInfo.category.color)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        onClick = onClick ?: {}
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = libraryInfo.name,
                style = MaterialTheme.typography.labelLarge,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = libraryInfo.category.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = textColor.copy(alpha = 0.7f),
                maxLines = 1
            )
        }
    }
}

/**
 * A category header for grouping libraries.
 */
@Composable
fun LibraryCategoryHeader(
    category: LibraryCategory,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color(category.color).copy(alpha = 0.2f),
            modifier = Modifier.size(8.dp)
        ) {}

        Text(
            text = category.displayName,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "($count)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
