package com.example.swrunevault.regex

class SpanishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {

        return Regex(
            """\+(\d+)\s+Runa\s+(\w+)\s+(\w+)\s+\((\d)\)"""
        )
    }
}