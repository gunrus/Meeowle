package ru.tinkoff.fintech.meowle.espresso

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.espresso.screens.EspressoAuthScreen
import ru.tinkoff.fintech.meowle.espresso.screens.EspressoSearchScreen
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

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
