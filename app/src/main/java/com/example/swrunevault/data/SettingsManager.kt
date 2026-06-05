package com.example.swrunevault.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.swrunevault.models.Language
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class SettingsManager(
    private val context: Context
) {
    companion object {
        val SYSTEM_DARK_MODE = booleanPreferencesKey("system_dark_mode")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val OPEN_SW = booleanPreferencesKey("open_sw")
    }

    val isSystemDarkMode = context.dataStore.data.map {preferences -> preferences[SYSTEM_DARK_MODE] ?: true }

    suspend fun setSystemDarkMode(enabled: Boolean) {
        context.dataStore.edit {
                settings ->
            settings[SYSTEM_DARK_MODE] = enabled
        }
    }

    val isDarkMode = context.dataStore.data.map {preferences -> preferences[DARK_MODE] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit {
                settings ->
            settings[DARK_MODE] = enabled
        }
    }

    val selectedLanguage = context.dataStore.data.map {preferences -> preferences[LANGUAGE] ?: Locale.getDefault().language}

    suspend fun setLanguage(language: String) {
        context.dataStore.edit {
                settings ->
            settings[LANGUAGE] = language
        }
    }

    suspend fun getLanguage(): Language? {
        return Language.fromCode(
            selectedLanguage.first()
        )
    }

    val isOpenSw = context.dataStore.data.map {preferences -> preferences[OPEN_SW] ?: true }

    suspend fun setOpenSw(enabled: Boolean) {
        context.dataStore.edit {
                settings ->
            settings[OPEN_SW] = enabled
        }
    }
}