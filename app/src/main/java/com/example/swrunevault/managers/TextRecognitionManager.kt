package com.example.swrunevault.managers

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextRecognitionManager {
    //Reconocedor OCR de ML Kit.
    private val recognizer =
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )

    //Analiza bitmap y devuelve texto reconocido.
    fun recognizeText(
        bitmap: Bitmap,
        onResult: (
            MutableList<
                    MutableList<com.google.mlkit.vision.text.Text.Line>
                    >
        ) -> Unit
    ){
        // Mostrar tamaño bitmap.
        Log.d(
            "OCR",
            "Bitmap Width: ${bitmap.width} | Height: ${bitmap.height}"
        )

        // Convertimos bitmap a InputImage.
        val image =
            InputImage.fromBitmap(
                bitmap,
                0
            )

        // Procesar OCR.
        recognizer.process(image)
            // OCR exitoso.
            .addOnSuccessListener { visionText ->
                // Lista donde guardaremos TODAS las líneas OCR.
                val allLines = mutableListOf<com.google.mlkit.vision.text.Text.Line>()

                // Recorremos todos los bloques y extraemos sus líneas.
                for (block in visionText.textBlocks) {
                    allLines.addAll(
                        block.lines
                    )
                }

                // Ordenamos inicialmente por Y (de arriba hacia abajo).
                val sortedByY =
                    allLines.sortedBy {
                        it.boundingBox?.top ?: 0
                    }

                // Aquí almacenaremos grupos de líneas visuales.
                val groupedLines =
                    mutableListOf<
                            MutableList<com.google.mlkit.vision.text.Text.Line>
                            >()

                // Diferencia máxima en Y para considerar misma fila.
                val threshold = 30

                // Agrupar líneas por cercanía en Y.
                for (line in sortedByY) {
                    val currentY = line.boundingBox?.top ?: 0

                    // Último grupo creado.
                    val lastGroup = groupedLines.lastOrNull()

                    // Si aún no existe grupo, crear el primero.
                    if (lastGroup == null) {
                        groupedLines.add(
                            mutableListOf(line)
                        )
                        continue
                    }

                    // Y de referencia del grupo.
                    val lastY = lastGroup.first().boundingBox?.top ?: 0

                    // Si están cerca verticalmente, pertenecen a la misma fila.
                    if (
                        kotlin.math.abs(
                            currentY - lastY
                        ) < threshold
                    ) {
                        lastGroup.add(line)
                    } else {
                        // Crear nueva fila.
                        groupedLines.add(
                            mutableListOf(line)
                        )
                    }
                }

                // Mostrar resultado final.
                /*for (group in groupedLines) {

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
                    }
                }*/

                onResult(
                    groupedLines
                )
            }
            // Error OCR.
            .addOnFailureListener {
                Log.e(
                    "OCR",
                    "Error OCR: ${it.message}"
                )
            }
    }
}