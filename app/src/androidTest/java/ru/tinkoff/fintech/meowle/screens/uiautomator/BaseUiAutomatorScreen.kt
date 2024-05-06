package ru.tinkoff.fintech.meowle.screens.uiautomator

import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

/**
 * @author Ruslan Ganeev
 */
abstract class BaseUiAutomatorScreen {

    protected val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()
    protected val uiDevice = UiDevice.getInstance(instrumentationRegistry)

    protected fun waitFindObject(selector: BySelector, timeout: Long = 3_000L): UiObject2 {
        return uiDevice.wait(Until.findObject(selector), timeout)
    }

    protected fun resourceString(@StringRes stringId: Int): String {
        return instrumentationRegistry.targetContext.getString(stringId)
    }
}