package ru.tinkoff.fintech.meowle.uiautomator

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.screens.uiautomator.UiAutomatorAuthScreen
import ru.tinkoff.fintech.meowle.screens.uiautomator.UiAutomatorSearchScreen

/**
 * @author Ruslan Ganeev
 */
@RunWith(AndroidJUnit4::class)
class UiAutomatorScreenExample {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun auth() {
        val authScreen = UiAutomatorAuthScreen()
        authScreen.enterName("Алексей")
        authScreen.enterPhone("+79999999999")
        authScreen.clickSubmit()

        val searchScreen = UiAutomatorSearchScreen()
        searchScreen.checkTitle()
    }
}