package com.example.swrunevault.models

data class RuneMainStatDefinition(

    // Tipo de propiedad.
    val statType: RuneStatType,

    // Valores según cantidad de estrellas.
    val values: Map<RuneGrade, MainStatValue>
)