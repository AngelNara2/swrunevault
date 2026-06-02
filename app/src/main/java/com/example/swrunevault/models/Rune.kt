package com.example.swrunevault.models

// Runa encontrada en el juego.
data class Rune(
    // Conjunto de la runa.
    val runeSet: RuneSet,

    // Slot de la runa.
    val slot: Int,

    // Cantidad de estrellas.
    val stars: Int,

    // Nivel de la runa.
    val level: Int,

    // Propiedad principal.
    val mainStat: RuneStat,

    // Propiedad innata.
    val innateStat: RuneStat?,

    // Propiedades secundarias.
    val subStats: List<RuneStat>
)