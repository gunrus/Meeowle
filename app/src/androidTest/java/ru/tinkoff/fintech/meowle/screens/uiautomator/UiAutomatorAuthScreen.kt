package ru.tinkoff.fintech.meowle.screens.uiautomator

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until

/**
 * @author Ruslan Ganeev
 */
class UiAutomatorAuthScreen {

    private val uiDevice = UiDevice.getInstance(getInstrumentation())
    private val nameField = By.res("ru.tinkoff.fintech.meowle", "et_name")
    private val phoneField = By.res("ru.tinkoff.fintech.meowle", "et_phone")
    private val submitButton = By.res("ru.tinkoff.fintech.meowle", "submit_button")
    private val waitTimeout = 3_000L

    fun enterName(name: String) {
        uiDevice.wait(Until.findObject(nameField), waitTimeout).text = name
    }

    fun enterPhone(phone: String) {
        uiDevice.wait(Until.findObject(phoneField), waitTimeout).text = phone
    }

    fun clickSubmit() {
        uiDevice.wait(Until.findObject(submitButton), waitTimeout).click()
    }
}