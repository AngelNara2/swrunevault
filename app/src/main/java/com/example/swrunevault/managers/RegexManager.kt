package com.example.swrunevault.managers

import com.example.swrunevault.models.Language
import com.example.swrunevault.regex.EnglishRegexProvider
import com.example.swrunevault.regex.RegexProvider
import com.example.swrunevault.regex.SpanishRegexProvider

class RegexManager(
    private val language: Language?
) {

    fun getProvider(): RegexProvider {

        return when (language) {

            Language.SPANISH ->
                SpanishRegexProvider()

            Language.ENGLISH ->
                EnglishRegexProvider()

            else -> {
                SpanishRegexProvider()
            }
        }
    }
}