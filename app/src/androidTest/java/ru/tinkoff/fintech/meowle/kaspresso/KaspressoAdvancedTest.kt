package ru.tinkoff.fintech.meowle.kaspresso

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.components.alluresupport.interceptors.step.AllureMapperStepInterceptor
import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.views.DumpViewsInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.text.KButton
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoAuthScreen

/**
 * @author Ruslan Ganeev
 */
class KaspressoAdvancedTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 3_000, intervalMs = 250)
        }
    ).apply {
        stepWatcherInterceptors.addAll(listOf(AllureMapperStepInterceptor()))
        testRunWatcherInterceptors.addAll(listOf(DumpViewsInterceptor(viewHierarchyDumper)))
    }
) {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun kautomatorAuth() = run {
        val authScreen = KaspressoAuthScreen()

        step("Вводим имя") {
            authScreen.enterName("Руслан")
        }

        step("Вводим номер телефона") {
            authScreen.enterPhone("+79999999999")
        }

        step("Нажимаем кнопку 'Войти'") {
            authScreen.clickSubmit()
        }

        Thread.sleep(3_000)
    }

    @Test
    fun withBeforeAndAfter() = before {
        println("Before")
    }.after {
        println("After")
    }.run {

        val authScreen = KaspressoAuthScreen()

        step("Вводим имя") {
            authScreen.enterName("Руслан")
        }

        step("Вводим номер телефона") {
            authScreen.enterPhone("+79999999999")
        }

        flakySafely(10_000) {
            KButton { withId(-1123) }.isDisplayed()
        }
    }
}