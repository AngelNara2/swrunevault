package com.example.swrunevault.models

data class RuneMainStatDefinition(

    // Tipo de propiedad.
    val statType: RuneStatType,

    // Valores según cantidad de estrellas.
    val values: Map<RuneGrade, MainStatValue>
){
    fun getMaxValue(
        grade: RuneGrade
    ): Double? {
        return values[grade]?.maxValue
    }
}