package com.example.swrunevault.models

// Representa los conjuntos disponibles de runas.
enum class RuneSet(
    // Cantidad de piezas necesarias para activar el efecto.
    val pieces: Int,

    // Descripción del efecto.
    val effect: String
) {
    ENERGY(2,"+15% HP"),
    FATAL(4,"+35% Ataque"),
    BLADE(2,"+12% Tasa crítica"),
    SWIFT(4,"+25% Velocidad"),
    FOCUS(2,"+20% Precisión"),
    GUARD(2,"+15% Defensa"),
    ENDURE(2,"+20% Resistencia"),
    SHIELD(2,"Escudo a aliados por 3 turnos (15% del HP)"),
    REVENGE(2,"+15% Probabilidad de contraatacar"),
    WILL(2,"+1 turno de inmunidad"),
    NEMESIS(2,"+4% barra de ataque por cada 7% HP perdido"),
    VAMPIRE(4,"+35% Chupasangre"),
    DESTROY(2,"30% daño infligido destruye HP MAX enemigo"),
    DESPAIR(4,"+25% Probabilidad de aturdir"),
    VIOLENT(4,"+22% Probabilidad de turno extra"),
    RAGE(4,"+40% Daño crítico"),
    FIGHT(2,"+7% Ataque aliados"),
    DETERMINATION(2,"+7% Defensa aliados"),
    ENHANCE(2,"+7% HP aliados"),
    ACCURACY(2,"+10% Precisión aliados"),
    TOLERANCE(2,"+10% Resistencia aliados"),
    SEAL(2,"Tasa de Sellado +25%"),
    INTANGIBLE(0,"");

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