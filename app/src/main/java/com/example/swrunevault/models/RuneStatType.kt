package com.example.swrunevault.models

// Tipos posibles de stats.
enum class RuneStatType(
    // Nombre mostrado.
    var displayName: String,

    // Indica si el stat és porcentual.
    val isPercentage: Boolean,

    val hasGrindsTone: Boolean,

    val grindstoneMinValue: Int,

    val grindstoneMaxValue: Int,

    var displayText: String = "",
) {
    // Nombre mostrado de la propiedad en En | Es
    UNKNOWN(
        "UNKNOWN",
        false,
        false,
        0 ,
        0
    ),

    HP(
        "HP",
        false,
        true,
        430,
        550),

    HP_PERCENT(
        "HP",
        true,
        true,
        5,
        10),

    ATK(
        "ATK|ATQ",
        false,
        true,
        18 ,
        30),

    ATK_PERCENT(
        "ATK|ATQ",
        true,
        true,
        5,
        10),

    DEF(
        "DEF",
        false,
        true,
        18,
        30),

    DEF_PERCENT(
        "DEF",
        true ,
        true,
        5,
        10),

    SPD(
        "SPD|VEL",
        false,
        true,
        4,
        5),

    CRIT_RATE(
        "CRI Rate|Tasa CRÍ",
        true,
        false,
        0 ,
        0),
    CRIT_DAMAGE(
        "CRI Dmg|Daño CRÍ",
        true,
        false,
        0 ,
        0),

    ACCURACY(
        "Accuracy|Precisión",
        true,
        false,
        0 ,
        0),

    RESISTANCE(
        "RES|Resistencia",
        true,
        false,
        0,
        0);

    companion object {
        // Buscar stat usando OCR.
        fun fromText(
            text: String,
            percentage: Boolean
        ): RuneStatType? {
            var runeStatType = entries.firstOrNull {
                it.displayName.contains(
                    text.trim(),
                    ignoreCase = true
                ) && it.isPercentage == percentage

            }

            val localizedText = getStatName(runeStatType?.displayName?:"",text.trim())

            runeStatType?.displayText = localizedText?:""

            return runeStatType
        }

        fun getStatName(text: String, searchTerm: String): String? {
            return text
                .split("|")
                .firstOrNull { it.contains(searchTerm, ignoreCase = true) }
        }
    }


}