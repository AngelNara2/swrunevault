package com.example.swrunevault.models

// Estadística de una runa.
data class RuneStat(
    // Tipo de estadística.
    val statType: RuneStatType?,

    // Valor actual.
    val value: Int
)