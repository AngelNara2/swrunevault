package com.example.swrunevault.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R

@Composable
fun SettingsScreen() {

    var darkMode by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Modo oscuro",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                }
            )
        }
    }
}