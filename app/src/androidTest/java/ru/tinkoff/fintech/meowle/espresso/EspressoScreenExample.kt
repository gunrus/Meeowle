package ru.tinkoff.fintech.meowle.espresso

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.screens.espresso.EspressoAuthScreen
import ru.tinkoff.fintech.meowle.screens.espresso.EspressoSearchScreen

/**
 * @author Ruslan Ganeev
 */
@RunWith(AndroidJUnit4::class)
class EspressoScreenExample {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun auth() {
        val authScreen = EspressoAuthScreen()

        val name = "Алексей"
        val phone = "+79999999999"

        authScreen.enterName(name)
        authScreen.enterPhone(phone)

        authScreen.checkName(name)
        authScreen.checkPhone(phone)

        authScreen.clickSubmit()

        val searchScreen = EspressoSearchScreen()

        val title = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.app_name)
        searchScreen.checkTitle(title)
    }
}