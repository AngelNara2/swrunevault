package com.example.swrunevault.models

// Idiomas soportados por la aplicación.
enum class Language(
    // Código ISO del idioma.
    val code: String,

    // Nombre visible para el usuario.
    val displayName: String
) {
    ENGLISH(
        "en",
        "English"
    ),
    SPANISH(
        "es",
        "Español"
    );

    companion object {
        // Obtiene el idioma a partir de su código.
        fun fromCode(
            code: String?
        ): Language? {
            return entries.firstOrNull {
                it.code.equals(
                    code,
                    ignoreCase = true
                )
            }
        }
    }
}