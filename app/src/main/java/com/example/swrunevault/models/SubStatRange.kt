package com.example.swrunevault.models

// Rango posible de una subestadística para una cantidad determinada de estrellas.
data class SubStatRange(
    // Valor mínimo posible.
    val minValue: Int,

    // Valor máximo posible.
    val maxValue: Int
)