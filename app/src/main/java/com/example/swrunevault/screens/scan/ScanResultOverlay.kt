package com.example.swrunevault.screens.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swrunevault.models.Rune

@Composable
fun ScanResultOverlay(
    rune: Rune,
    onClose: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.85f)
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Overlay funcionando",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Set: ${rune.runeSet}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Slot: ${rune.slot}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Nivel: +${rune.level}",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                modifier = Modifier.padding(top = 24.dp),
                onClick = onClose
            ) {
                Text("Cerrar")
            }
        }
    }
}