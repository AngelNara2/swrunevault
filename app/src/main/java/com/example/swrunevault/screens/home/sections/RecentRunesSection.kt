package com.example.swrunevault.screens.home.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swrunevault.components.RuneItem

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
            RuneItem(
                runeName = "Violent",
                runeStars = 6,
                runePrincipalStat = "+12 ATQ %",
                runeSlot = 4
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 1.dp)
            )
            RuneItem(
                runeName = "Swift",
                runeStars = 6,
                runePrincipalStat = "+15 HP %",
                runeSlot = 6
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 1.dp)
            )
            RuneItem(
                runeName = "Rage",
                runeStars = 6,
                runePrincipalStat = "+9 VEL",
                runeSlot = 1
            )
        }
    }
}