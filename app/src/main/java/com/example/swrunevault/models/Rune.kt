package com.example.swrunevault.models

// Runa encontrada en el juego.
data class Rune(
    // Conjunto de la runa.
    var runeSet: RuneSet = RuneSet.UNKNOWN,

    // Slot de la runa.
    var slot: Int = 0,

    // Cantidad de estrellas.
    var stars: Int = 0,

    // Nivel de la runa.
    var level: Int = 0,

    // Propiedad principal.
    var mainStat: RuneStat? = null,

    // Propiedad innata.
    var innateStat: RuneInnateStat? = null,

    // Propiedades secundarias.
    var subStats: MutableList<RuneStat> = mutableListOf()
)