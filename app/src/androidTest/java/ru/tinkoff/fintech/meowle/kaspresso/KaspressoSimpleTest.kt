package ru.tinkoff.fintech.meowle.kaspresso

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoAuthScreen
import ru.tinkoff.fintech.meowle.screens.kaspresso.KautomatorAuthScreen
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


    @Test
    fun kaspressoAuth() = run {
        val authScreen = KaspressoAuthScreen()

        authScreen.enterName("Руслан")
        authScreen.enterPhone("+79999999999")
        authScreen.clickSubmit()

        sleep(3_000)
    }
}