package ru.tinkoff.fintech.meowle.testing.prefs

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import ru.tinkoff.fintech.meowle.presentation.Mode
import androidx.core.content.edit

/**
 * @author Ruslan Ganeev
 */
object MeowlePrefs {

    private val prefs = InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .getSharedPreferences("meowle", Context.MODE_PRIVATE)

    fun authorize() {
        prefs.edit { putBoolean("auth", true) }
    }

    fun unAuthorize() {
        prefs.edit { putBoolean("auth", false) }
    }

    fun changeAppUrl(url: String = "http://localhost:5000") {
        prefs.edit { putString("url", url) }
    }

    fun changeUiMode(mode: Mode = Mode.VIEWS) {
        prefs.edit { putString("launch_mode", mode.name) }
    }

    fun clear() {
        prefs.edit { clear() }
    }
}
