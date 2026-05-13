package com.example.swrunevault.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swrunevault.components.RuneItem
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

@Composable
fun RecentRunesSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Últimas runas agregadas",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = Color.Transparent
                ) {
                    Text(
                        text = "Ver todas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            //Spacer(modifier = Modifier.height(16.dp))
            RuneItem(
                runeName = "Violent",
                runeStats = "6★ +12 ATQ %",
                runeTime = "Agregada hace 2 min"
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 1.dp)
            )
            RuneItem(
                runeName = "Swift",
                runeStats = "6★ +15 HP %",
                runeTime = "Agregada hace 15 min"
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 1.dp)
            )
            RuneItem(
                runeName = "Rage",
                runeStats = "6★ +9 VEL",
                runeTime = "Agregada hace 32 min"
            )
        }
    }
}