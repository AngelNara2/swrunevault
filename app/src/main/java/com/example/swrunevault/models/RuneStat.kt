package com.example.swrunevault.models

// Estadística de una runa.
data class RuneStat(
    // Tipo de estadística.
    val statType: RuneStatType?,

    // Valor actual.
    val value: Int
){
    fun secondaryStat(): String{
        return "${statType?.displayText} " +
                "+${value}" +
                if (statType?.isPercentage== true) "%" else ""
    }
}