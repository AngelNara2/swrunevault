package com.example.swrunevault.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R

@Composable
fun HelpScreen() {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Ayuda",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Pantalla de ayuda")
    }
}