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

    val enchantedMaxValue: Int,

    val idStatResource: Int,

    var displayText: String = "",

    var isEnchanted: Boolean = false,

    var slots: IntArray
) {
    // Nombre mostrado de la propiedad en En | Es
    UNKNOWN(
        "UNKNOWN",
        false,
        false,
        0 ,
        0,
        0,
        R.drawable.substat_atk,
        slots = intArrayOf(0)
    ),

    HP(
        "HP",
        false,
        true,
        430,
        550,
        580,
        R.drawable.substat_hp,
        slots = intArrayOf(1,2,3,4,6)
        ),

    HP_PERCENT(
        "HP",
        true,
        true,
        5,
        10,
        13,
        R.drawable.substat_hp,
        slots = intArrayOf(1,2,3,4,5,6)
    ),

    ATK(
        "ATK|ATQ",
        false,
        true,
        18,
        30,
        40,
        R.drawable.substat_atk,
        slots = intArrayOf(2,4,5,6)
    ),

    ATK_PERCENT(
        "ATK|ATQ",
        true,
        true,
        5,
        10,
        13,
        R.drawable.substat_atk,
        slots = intArrayOf(1,2,4,5,6)
    ),

    DEF(
        "DEF",
        false,
        true,
        18,
        30,
        40,
        R.drawable.substat_def,
        slots = intArrayOf(2,4,5,6)
    ),

    DEF_PERCENT(
        "DEF",
        true ,
        true,
        5,
        10,
        13,
        R.drawable.substat_def,
        slots = intArrayOf(2,3,4,5,6)
    ),

    SPD(
        "SPD|VEL",
        false,
        true,
        4,
        5,
        10,
        R.drawable.substat_spd,
        slots = intArrayOf(1,2,3,4,5,6)
    ),

    CRIT_RATE(
        "CRI Rate|Tasa CRÍ",
        true,
        false,
        0 ,
        0,
        9,
        R.drawable.substat_crirate,
        slots = intArrayOf(1,2,3,4,5,6)
    ),
    CRIT_DAMAGE(
        "CRI Dmg|Daño CRÍ",
        true,
        false,
        0 ,
        0,
        10,
        R.drawable.substat_cridmg,
        slots = intArrayOf(1,2,3,4,5,6)
    ),

    ACCURACY(
        "Accuracy|Precisión",
        true,
        false,
        0 ,
        0,
        11,
        R.drawable.substat_acc,
        slots = intArrayOf(1,2,3,4,5,6)
    ),

    RESISTANCE(
        "RES|Resistencia",
        true,
        false,
        0,
        0,
        11,
        R.drawable.substat_res,
        slots = intArrayOf(1,2,3,4,5,6)
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