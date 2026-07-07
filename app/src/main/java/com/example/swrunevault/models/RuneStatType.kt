package com.example.swrunevault.models

import com.example.swrunevault.R
// Tipos posibles de stats.
enum class RuneStatType(
    // Nombre mostrado.
    var displayName: String,

    // Indica si el stat és porcentual.
    val isPercentage: Boolean,

    val hasGrindsTone: Boolean,

    val grindstoneMinValue: Int,

    val grindstoneMaxValue: Int,

    val idStatResource: Int,

    var displayText: String = "",

    var isEnchanted: Boolean = false
) {
    // Nombre mostrado de la propiedad en En | Es
    UNKNOWN(
        "UNKNOWN",
        false,
        false,
        0 ,
        0,
        R.drawable.substat_atk
    ),

    HP(
        "HP",
        false,
        true,
        430,
        550,
        R.drawable.substat_hp
        ),

    HP_PERCENT(
        "HP",
        true,
        true,
        5,
        10,
        R.drawable.substat_hp
    ),

    ATK(
        "ATK|ATQ",
        false,
        true,
        18 ,
        30,
        R.drawable.substat_atk
    ),

    ATK_PERCENT(
        "ATK|ATQ",
        true,
        true,
        5,
        10,
        R.drawable.substat_atk
    ),

    DEF(
        "DEF",
        false,
        true,
        18,
        30,
        R.drawable.substat_def
    ),

    DEF_PERCENT(
        "DEF",
        true ,
        true,
        5,
        10,
        R.drawable.substat_def
    ),

    SPD(
        "SPD|VEL",
        false,
        true,
        4,
        5,
        R.drawable.substat_spd
    ),

    CRIT_RATE(
        "CRI Rate|Tasa CRÍ",
        true,
        false,
        0 ,
        0,
        R.drawable.substat_crirate
    ),
    CRIT_DAMAGE(
        "CRI Dmg|Daño CRÍ",
        true,
        false,
        0 ,
        0,
        R.drawable.substat_cridmg
    ),

    ACCURACY(
        "Accuracy|Precisión",
        true,
        false,
        0 ,
        0,
        R.drawable.substat_acc
    ),

    RESISTANCE(
        "RES|Resistencia",
        true,
        false,
        0,
        0,
        R.drawable.substat_res
    );

    companion object {
        // Buscar stat usando OCR.
        fun fromText(
            text: String,
            percentage: Boolean
        ): RuneStatType {
            var runeStatType = entries.firstOrNull() {
                it.displayName.contains(
                    text.trim(),
                    ignoreCase = true
                ) && it.isPercentage == percentage
            }

            if(runeStatType == null) {runeStatType = UNKNOWN}

            val localizedText = getStatName(runeStatType.displayName,text.trim())

            runeStatType.displayText = localizedText?:""

            return runeStatType
        }

        fun getStatName(text: String, searchTerm: String): String? {
            return text
                .split("|")
                .firstOrNull { it.contains(searchTerm, ignoreCase = true) }
        }
    }


}