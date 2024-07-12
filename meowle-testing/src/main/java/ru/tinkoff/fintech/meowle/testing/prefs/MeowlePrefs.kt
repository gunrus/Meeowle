package ru.tinkoff.fintech.meowle.testing.prefs

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import ru.tinkoff.fintech.meowle.presentation.Mode

/**
 * @author Ruslan Ganeev
 */
object MeowlePrefs {

    private val prefs = InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .getSharedPreferences("meowle", Context.MODE_PRIVATE)

    fun authorize() {
        prefs
            .edit()
            .putBoolean("auth", true)
            .apply()
    }

    fun unauthorize() {
        prefs
            .edit()
            .putBoolean("auth", false)
            .apply()
    }

    fun changeAppUrl(url: String = "http://localhost:5000") {
        prefs
            .edit()
            .putString("url", url)
            .apply()
    }

    fun changeUiMode(mode: Mode = Mode.VIEWS) {
        prefs
            .edit()
            .putString("launch_mode", mode.name)
            .apply()
    }

    fun clear() {
        prefs
           .edit()
           .clear()
           .apply()
    }
}
