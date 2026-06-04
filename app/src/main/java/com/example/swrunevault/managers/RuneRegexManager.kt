package com.example.swrunevault.managers

import android.util.Log
import com.example.swrunevault.regex.RegexProvider

class RuneRegexManager(
    private val regexProvider: RegexProvider
) {
    fun analyze(
        groupedLines:
        MutableList<
                MutableList<com.google.mlkit.vision.text.Text.Line>
                >
    ) {

        val headerRegex = regexProvider.runeHeader()

        for (group in groupedLines) {

            // Ordenar fila de izquierda a derecha.
            val sortedRow =
                group.sortedBy {
                    it.boundingBox?.left ?: 0
                }

            Log.d(
                "OCR",
                "===================="
            )

            //Mostrar elementos ya ordenados.
            for (line in sortedRow) {
                val text =
                    line.text
                val box =
                    line.boundingBox
                val x =
                    box?.left ?: 0
                val y =
                    box?.top ?: 0

                Log.d(
                    "OCR",
                    "Texto: $text | X:$x Y:$y"
                )

                val match =
                    headerRegex.find(
                        text
                    )

                if (match != null) {
                    Log.d(
                        "RUNE_REGEX",
                        "Nivel: ${match.groupValues[1]}"
                    )

                    Log.d(
                        "RUNE_REGEX",
                        "Innate: ${match.groupValues[2]}"
                    )

                    Log.d(
                        "RUNE_REGEX",
                        "Set: ${match.groupValues[3]}"
                    )

                    Log.d(
                        "RUNE_REGEX",
                        "Slot: ${match.groupValues[4]}"
                    )
                }
            }
        }
    }
}