package com.example.swrunevault.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R

@Composable
fun RuneCard(){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Runa Violent",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("⭐ Grado: 6")
            Text("💪 Ataque: +63")
            Text("⚡ Velocidad: +12")
            Text("❤️ HP: +8%")
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
