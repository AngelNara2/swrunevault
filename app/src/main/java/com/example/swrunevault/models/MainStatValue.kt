package com.example.swrunevault.models

// Valores de una propiedad principal para una cantidad específica de estrellas.
data class MainStatValue(
    // Valor al nivel +0.
    val initialValue: Double,

    // Valor que aumenta por nivel.
    val levelIncrement: Double,

    // Valor máximo al nivel +15.
    val maxValue: Double
)