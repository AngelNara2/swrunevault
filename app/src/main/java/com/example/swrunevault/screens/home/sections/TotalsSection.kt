package com.example.swrunevault.screens.home.sections

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swrunevault.R
import com.example.swrunevault.components.ActionCard

@Composable
fun TotalsSection(){
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
                        contentDescription = stringResource(R.string.totals),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = stringResource(R.string.totals),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                ActionCard(
                    modifier = Modifier.weight(1f),
                    cardSize = 100,
                    imageRes = R.drawable.rune_rage,
                    imageSize = 55,
                    imagebackground = Color(0xFFF3EFFD),
                    title = stringResource(R.string.total_runes),
                    titleBold = false,
                    subtitle = "245",
                    subtitleBold = true,

                    primaryColor = Color(0xFF542FA2),
                    onClick = {
                    }
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    cardSize = 100,
                    imageRes = R.drawable.action_scan,
                    imageSize = 55,
                    imagebackground = Color(0xFFFDECD8),
                    title = stringResource(R.string.legendary),
                    titleBold = false,
                    subtitle = "35",
                    subtitleBold = true,

                    primaryColor = Color(0xFFFC9104),
                    onClick = {
                    }
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    cardSize = 100,
                    imageRes = R.drawable.action_inventory,
                    imageSize = 55,
                    imagebackground = Color(0xFFEBF3FE),
                    title = stringResource(R.string.sets),
                    titleBold = false,
                    subtitle = "35",
                    subtitleBold = true,
                    primaryColor = Color(0xFF1D57AF),
                    onClick = {
                    }
                )
                ActionCard(
                    modifier = Modifier.weight(1f),
                    cardSize = 100,
                    imageRes = R.drawable.action_scan,
                    imageSize = 55,
                    imagebackground = Color(0xFFE3F4E4),
                    title = stringResource(R.string.in_use),
                    titleBold = false,
                    subtitle = "85%",
                    subtitleBold = true,
                    primaryColor = Color(0xFF219044),
                    onClick = {
                    }
                )
            }
        }
    }
}