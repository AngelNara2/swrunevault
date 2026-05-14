package com.example.swrunevault.screens.home.sections

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.ActionCard

@Composable
fun TotalsSection(){
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
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.size(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.action_scan),
                        contentDescription = "Totales",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = "Totales",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                NewActionCard(
                    title = "Total Runas",
                    subtitle = "245",
                    imageRes = R.drawable.rune_rage,
                    backgroundColor = Color(0xFFF3EFFD),
                    primaryColor = Color(0xFF542FA2),
                    onClick = {
                    },
                    modifier = Modifier.weight(1f)
                )
                NewActionCard(
                    title = "Legendarias",
                    subtitle = "35",
                    imageRes = R.drawable.action_scan,
                    backgroundColor = Color(0xFFFDECD8),
                    primaryColor = Color(0xFFFC9104),
                    onClick = {
                    },
                    modifier = Modifier.weight(1f)
                )
                NewActionCard(
                    title = "Hero",
                    subtitle = "35",
                    imageRes = R.drawable.action_inventory,
                    backgroundColor = Color(0xFFEBF3FE),
                    primaryColor = Color(0xFF1D57AF),
                    onClick = {
                    },
                    modifier = Modifier.weight(1f)
                )
                NewActionCard(
                    title = "En uso",
                    subtitle = "85%",
                    imageRes = R.drawable.action_scan,
                    backgroundColor = Color(0xFFE3F4E4),
                    primaryColor = Color(0xFF219044),
                    onClick = {
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun NewActionCard(@DrawableRes imageRes: Int, title: String, subtitle: String, backgroundColor: Color, primaryColor: Color, onClick: () -> Unit,modifier: Modifier){
    ActionCard(
        cardSize = 100,
        title = title,
        imageSize = 35,
        titleBold = false,
        subtitle = subtitle,
        subtitleBold = true,
        backgroundColor = backgroundColor,
        primaryColor = primaryColor,
        imageRes = imageRes,
        onClick = {
        },
        modifier = modifier
    )
}