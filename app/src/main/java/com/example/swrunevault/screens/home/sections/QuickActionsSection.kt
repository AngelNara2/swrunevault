package com.example.swrunevault.screens.home.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.ActionCard

@Composable
fun QuickActionsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionCard(
            title = "Escanear",
            subtitle = "Escanea tus runas",
            backgroundColor = Color(0xFFF3EFFD),
            primaryColor = Color(0xFF542FA2),
            imageRes = R.drawable.action_scan,
            onClick = {
            },
            modifier = Modifier.weight(1f)
        )
        ActionCard(
            title = "Inventario",
            subtitle = "Ver todas tus runas",
            backgroundColor = Color(0xFFEBF3FE),
            primaryColor = Color(0xFF1D57AF),
            imageRes = R.drawable.action_inventory,
            onClick = {
            },
            modifier = Modifier.weight(1f)
        )
    }
}