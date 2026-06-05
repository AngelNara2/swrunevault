package com.example.swrunevault.managers

import android.util.Log
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneInnateStat
import com.example.swrunevault.models.RuneSet
import com.example.swrunevault.regex.RegexProvider

class RuneRegexManager(
    private val regexProvider: RegexProvider
) {
    private lateinit var rune: Rune

    fun analyze(
        groupedLines:
        MutableList<
                MutableList<com.google.mlkit.vision.text.Text.Line>
                >
    ) {
        val headerRegex = regexProvider.runeHeader()

        rune = Rune()

        for (group in groupedLines) {
            // Ordenar fila de izquierda a derecha.
            val sortedRow =
                group.sortedBy {
                    it.boundingBox?.left ?: 0
                }

            Log.d("OCR","====================")

            //Mostrar elementos ya ordenados.
            for (line in sortedRow) {
                val text = line.text
                val box = line.boundingBox
                val x = box?.left ?: 0
                val y = box?.top ?: 0

                Log.d("OCR","Texto: $text | X:$x Y:$y")

                val matchHeader = headerRegex.find(text)

                if (matchHeader != null) {
                    /*
                    Log.d("RUNE_REGEX","Nivel: ${matchHeader.groupValues[1]}")
                    Log.d("RUNE_REGEX","Innate: ${matchHeader.groupValues[2]}")
                    Log.d("RUNE_REGEX","Set: ${matchHeader.groupValues[3]}")
                    Log.d("RUNE_REGEX","Slot: ${matchHeader.groupValues[4]}")
                    */
                    rune.level = matchHeader.groupValues[1].toInt()
                    rune.innateStat = RuneInnateStat.fromText(matchHeader.groupValues[2]) ?: RuneInnateStat.UNKNOWN
                    rune.runeSet = RuneSet.fromText(matchHeader.groupValues[3]) ?: RuneSet.UNKNOWN
                    rune.slot = matchHeader.groupValues[4].toInt()
                }
            }
        }

        Log.d("RUNE_CREATE","Nivel: ${rune.level}")
        Log.d("RUNE_CREATE","Innate: ${rune.innateStat}")
        Log.d("RUNE_CREATE","Set: ${rune.runeSet}")
        Log.d("RUNE_CREATE","Slot: ${rune.slot}")
    }
}