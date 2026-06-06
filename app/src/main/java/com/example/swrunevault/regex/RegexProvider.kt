package com.example.swrunevault.regex

interface RegexProvider {
    fun runeHeader(): Regex

    fun runeStat(): Regex
}