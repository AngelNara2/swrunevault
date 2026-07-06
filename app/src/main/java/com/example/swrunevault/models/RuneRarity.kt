package com.example.swrunevault.models

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.example.swrunevault.R

enum class RuneRarity(
    val displayName: String,
    @ColorRes val colorBackground: Int,
    @ColorRes val colorText: Int
) {
    NORMAL(
        displayName = "Normal",
        colorBackground = R.color.rarity_normal_primary,
        colorText = R.color.rarity_normal_secondary
    ),

    MAGIC(
        displayName = "Magic",
        colorBackground = R.color.rarity_magic_primary,
        colorText = R.color.rarity_magic_secondary
    ),

    RARE(
        displayName = "Rare",
        colorBackground = R.color.rarity_rare_primary,
        colorText = R.color.rarity_rare_secondary
    ),

    HERO(
        displayName = "Hero",
        colorBackground = R.color.rarity_hero_primary,
        colorText = R.color.rarity_rare_secondary
    ),

    LEGENDARY(
        displayName = "Legend",
        colorBackground = R.color.rarity_legend_primary,
        colorText = R.color.rarity_legend_secondary
    ),

    UNKNOWN(
        displayName = "Unknown",
        colorBackground = R.color.rarity_unknown_primary,
        colorText = R.color.rarity_unknown_secondary
    );

    companion object {
        fun fromText(text: String): RuneRarity? {
            return entries.firstOrNull {
                it.displayName.equals(
                    text.trim(),
                    ignoreCase = true
                )
            }
        }
    }
}