package com.stacksense.ui.screens.settings

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stacksense.R
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stacksense.data.repository.DarkThemeConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val preferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    var showThemeDialog by remember { mutableStateOf(false) }

    if (showThemeDialog && preferences != null) {
        ThemeDialog(
            currentConfig = preferences!!.darkThemeConfig,
            onDismiss = { showThemeDialog = false },
            onSelectInfo = { config ->
                viewModel.updateDarkThemeConfig(config)
                showThemeDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Appearance Section
            SettingsSectionHeader(title = stringResource(R.string.appearance_header))
            
            SettingsItem(
                title = stringResource(R.string.app_theme_title),
                subtitle = when (preferences?.darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> stringResource(R.string.theme_system)
                    DarkThemeConfig.LIGHT -> stringResource(R.string.theme_light)
                    DarkThemeConfig.DARK -> stringResource(R.string.theme_dark)
                    null -> stringResource(R.string.loading)
                },
                onClick = { showThemeDialog = true }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                SettingsSwitchItem(
                    title = stringResource(R.string.dynamic_color_title),
                    subtitle = stringResource(R.string.dynamic_color_subtitle),
                    checked = preferences?.useDynamicColors ?: true,
                    onCheckedChange = { viewModel.updateDynamicColorPreference(it) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // General Section
            SettingsSectionHeader(title = stringResource(R.string.general_header))

            SettingsSwitchItem(
                title = stringResource(R.string.show_system_apps_title),
                subtitle = stringResource(R.string.show_system_apps_subtitle),
                checked = preferences?.showSystemApps ?: false,
                onCheckedChange = { viewModel.updateShowSystemApps(it) }
            )
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun ThemeDialog(
    currentConfig: DarkThemeConfig,
    onDismiss: () -> Unit,
    onSelectInfo: (DarkThemeConfig) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.choose_theme_dialog)) },
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                ThemeRadioOption(
                    title = stringResource(R.string.theme_system),
                    selected = currentConfig == DarkThemeConfig.FOLLOW_SYSTEM,
                    onClick = { onSelectInfo(DarkThemeConfig.FOLLOW_SYSTEM) }
                )
                ThemeRadioOption(
                    title = stringResource(R.string.theme_light),
                    selected = currentConfig == DarkThemeConfig.LIGHT,
                    onClick = { onSelectInfo(DarkThemeConfig.LIGHT) }
                )
                ThemeRadioOption(
                    title = stringResource(R.string.theme_dark),
                    selected = currentConfig == DarkThemeConfig.DARK,
                    onClick = { onSelectInfo(DarkThemeConfig.DARK) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun ThemeRadioOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null // handled by Row clickable
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
