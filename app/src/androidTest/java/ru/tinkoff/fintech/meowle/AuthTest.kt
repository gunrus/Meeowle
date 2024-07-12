package ru.tinkoff.fintech.meowle

import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.testing.screens.AuthScreen
import ru.tinkoff.fintech.meowle.testing.screens.SearchScreen

/**
 * @author Ruslan Ganeev
 */
class AuthTest : BaseTest() {

    @get:Rule
    val activityRule = activityScenarioRule<AuthActivity>()

    @Test
    fun auth() = run {
        AuthScreen(this) {
            enterName("Руслан")
            enterPhone("+79999999999")
            clickLogin()
        }

        SearchScreen(this) {
            checkScreenOpened()
        }
    }
}