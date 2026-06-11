package com.example.swrunevault.models

import com.example.swrunevault.R

// Representa los conjuntos disponibles de runas.
enum class RuneSet(
    // Cantidad de piezas necesarias para activar el efecto.
    val pieces: Int,

    // Descripción del efecto.
    val effect: String,

    val idRuneResource: Int
) {
    UNKNOWN(0,"",R.drawable.rune_violent),
    ENERGY(2,"+15% HP",R.drawable.rune_energy),
    FATAL(4,"+35% Ataque",R.drawable.rune_fatal),
    BLADE(2,"+12% Tasa crítica",R.drawable.rune_blade),
    SWIFT(4,"+25% Velocidad",R.drawable.rune_swift),
    FOCUS(2,"+20% Precisión",R.drawable.rune_focus),
    GUARD(2,"+15% Defensa",R.drawable.rune_guard),
    ENDURE(2,"+20% Resistencia",R.drawable.rune_endure),
    SHIELD(2,"Escudo a aliados por 3 turnos (15% del HP)",R.drawable.rune_shield),
    REVENGE(2,"+15% Probabilidad de contraatacar",R.drawable.rune_revenge),
    WILL(2,"+1 turno de inmunidad",R.drawable.rune_will),
    NEMESIS(2,"+4% barra de ataque por cada 7% HP perdido",R.drawable.rune_nemesis),
    VAMPIRE(4,"+35% Chupasangre",R.drawable.rune_vampire),
    DESTROY(2,"30% daño infligido destruye HP MAX enemigo",R.drawable.rune_destroy),
    DESPAIR(4,"+25% Probabilidad de aturdir",R.drawable.rune_despair),
    VIOLENT(4,"+22% Probabilidad de turno extra",R.drawable.rune_violent),
    RAGE(4,"+40% Daño crítico",R.drawable.rune_rage),
    FIGHT(2,"+7% Ataque aliados",R.drawable.rune_fight),
    DETERMINATION(2,"+7% Defensa aliados",R.drawable.rune_determination),
    ENHANCE(2,"+7% HP aliados",R.drawable.rune_enhance),
    ACCURACY(2,"+10% Precisión aliados",R.drawable.rune_accuracy),
    TOLERANCE(2,"+10% Resistencia aliados",R.drawable.rune_tolerance),
    SEAL(2,"Tasa de Sellado +25%",R.drawable.rune_seal),
    INTANGIBLE(0,"",R.drawable.rune_intangible);

    companion object {
        // Buscar set usando texto OCR.
        fun fromText(
            text: String
        ): RuneSet? {
            return entries.firstOrNull {
                it.name.equals(
                    text.trim(),
                    ignoreCase = true
                )
            }
        }
    }
}