package ru.tinkoff.fintech.meowle

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.testing.screens.KautomatorAuthScreen
import java.lang.Thread.sleep

/**
 * @author Ruslan Ganeev
 */
class KaspressoSimpleTest : TestCase() {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun kautomatorAuth() = run {
        val authScreen = KautomatorAuthScreen()

        authScreen.enterName("Руслан")
        authScreen.enterPhone("+79999999999")
        authScreen.clickSubmit()

        sleep(3_000)
    }
}
