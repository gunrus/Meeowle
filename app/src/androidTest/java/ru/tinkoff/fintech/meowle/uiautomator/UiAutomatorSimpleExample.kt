package ru.tinkoff.fintech.meowle.uiautomator

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

/**
 * @author Ruslan Ganeev
 */
@RunWith(AndroidJUnit4::class)
class UiAutomatorSimpleExample {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun auth() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val uiDevice = UiDevice.getInstance(instrumentation)

        val nameSelector = By.res("ru.tinkoff.fintech.meowle", "et_name")
        val nameField = uiDevice.findObject(nameSelector)
        nameField.text = "Алексей"

        val phoneSelector = By.res("ru.tinkoff.fintech.meowle", "et_phone")
        val phoneField = uiDevice.findObject(phoneSelector)
        phoneField.text = "+79999999999"

        val submitSelector = By.res("ru.tinkoff.fintech.meowle", "submit_button")
        val submitField = uiDevice.findObject(submitSelector)
        submitField.click()

        val selector = By.res("ru.tinkoff.fintech.meowle", "tw_search_title")
        val searchTitleText = uiDevice.findObject(selector).text
        assertEquals("Meowle", searchTitleText)
    }
}
