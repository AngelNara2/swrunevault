package com.example.swrunevault.screens.settings.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.data.SettingsManager
import com.example.swrunevault.models.Language
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun AppearanceSection(){
    val context = LocalContext.current

    val settingsManager = remember {
        SettingsManager(context)
    }

    val scope = rememberCoroutineScope()

    val systemDarkMode by settingsManager
        .isSystemDarkMode
        .collectAsState(initial = false)

    val darkMode by settingsManager
        .isDarkMode
        .collectAsState(initial = false)

    var expanded by remember {
        mutableStateOf(false)
    }

    val selectedLanguage by settingsManager
        .selectedLanguage
        .collectAsState(
            initial = LocalConfiguration.current.locales[0].language
        )

    val language = Language.fromCode(selectedLanguage) ?: Language.SPANISH

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Card(
                    modifier = Modifier.size(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.action_scan),
                        contentDescription = stringResource(R.string.appearance),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = stringResource(R.string.appearance),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de runa
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Información principal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.system_theme),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.use_system_theme),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = systemDarkMode,
                    onCheckedChange = {
                        scope.launch {
                            settingsManager.setSystemDarkMode(it)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de runa
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Información principal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.dark_mode),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.activate_dark_mode),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = darkMode,
                    onCheckedChange = {
                        scope.launch {
                            settingsManager.setDarkMode(it)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de runa
                Card(
                    modifier = Modifier.size(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.rune_violent),
                        contentDescription = "Runa Violent",
                        modifier = Modifier.fillMaxSize().padding(5.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                // Información principal
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.language),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.select_language),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column {
                    Box {
                        OutlinedButton(
                            modifier = Modifier.width(125.dp),
                            onClick = {
                                expanded = true
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(language.displayName)
                                Text("▼")
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            Language.entries.forEach { language ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            language.displayName
                                        )
                                    },
                                    onClick = {
                                        scope.launch {
                                            settingsManager.setLanguage(language.code)
                                        }
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}