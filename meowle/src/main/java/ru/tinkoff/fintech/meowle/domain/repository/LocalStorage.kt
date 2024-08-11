package ru.tinkoff.fintech.meowle.domain.repository

import android.content.Context

/**
 * @author Ruslan Ganeev
 */

private const val SHARED_PREFS_NAME = "meowle"

class LocalStorage(context: Context) {

    private val meowlePrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getString(paramName: String): String? {
        return meowlePrefs.getString(paramName, null)
    }

    fun setString(paramName: String, value: String) {
        meowlePrefs.edit().putString(paramName, value).apply()
    }

    fun getBoolean(paramName: String): Boolean {
        return meowlePrefs.getBoolean(paramName, false)
    }

    fun setBoolean(paramName: String, value: Boolean) {
        meowlePrefs.edit().putBoolean(paramName, value).apply()
    }
}