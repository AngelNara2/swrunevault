package com.example.swrunevault.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.RuneHeader
import com.example.swrunevault.components.RuneProperties
import com.example.swrunevault.components.StarBadge
import com.example.swrunevault.models.RuneRarity

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape,dpi=420"
)
@Composable
fun ScanResultScreen(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Card(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            )
        ) {
            RuneHeader(
                R.drawable.rune_rage,
                start = 6,
                runeTitle = "+15 Rage",
                rarity = RuneRarity.LEGENDARY,
                slot = 1,
                scanTime = "22/11/2024 14:35",
                location = "Inventario"
            )
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3EFFD)
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFF3EFFD)
                    )
                ){
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ){
                        Row {
                            Text(
                                text = "Estrellas",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f).padding(5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 1,
                                isSelect = false
                            )
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 2,
                                isSelect = false
                            )
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 3,
                                isSelect = false
                            )
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 4,
                                isSelect = false
                            )
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 5,
                                isSelect = false
                            )
                            StarBadge(
                                modifier = Modifier.weight(1f),
                                star = 6,
                                isSelect = true
                            )
                        }
                        Row(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "(Los cálculos se realizan en base a ★)",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            )
        ) {
            RuneProperties()
        }
        Card(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.LightGray
            )
        ) {

        }
    }
}