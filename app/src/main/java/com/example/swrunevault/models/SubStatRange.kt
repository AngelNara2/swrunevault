package com.example.swrunevault.models

// Rango posible de una subestadística para una cantidad determinada de estrellas.
data class SubStatRange(
    // Valor mínimo posible.
    val minValue: Double,

    // Valor máximo posible.
    val maxValue: Double
)