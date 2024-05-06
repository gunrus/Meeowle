package ru.tinkoff.fintech.meowle.wiremock

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/**
 * @author Ruslan Ganeev
 */
object WireMockHelper {

    fun fileToString(path: String, context: Context = InstrumentationRegistry.getInstrumentation().context): String {
        return BufferedReader(InputStreamReader(context.assets.open(path), StandardCharsets.UTF_8)).readText()
    }
}