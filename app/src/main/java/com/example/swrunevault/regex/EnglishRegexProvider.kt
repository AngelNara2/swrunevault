package com.example.swrunevault.regex

class EnglishRegexProvider :
    RegexProvider {

    override fun runeHeader(): Regex {

        return Regex(
            """\+(\d+)\s+(\w+)\s+(\w+)\s+Rune\s+\((\d)\)"""
        )
    }
}