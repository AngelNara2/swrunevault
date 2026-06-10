package com.example.swrunevault.models

/**
 * Definición de una propiedad secundaria.
 *
 * Esta definición aplica tanto para:
 *
 * - Valor inicial de la subestadística.
 * - Incrementos obtenidos en los rolls
 *   (+3, +6, +9 y +12).
 */
data class RuneSubStatDefinition(
    // Tipo de estadística.
    val statType: RuneStatType,

    // Rangos por cantidad de estrellas.
    val ranges: Map<RuneGrade, SubStatRange>
){
    fun getMaxValue(
        grade: RuneGrade
    ): Int? {
        return ranges[grade]?.maxValue
    }
}