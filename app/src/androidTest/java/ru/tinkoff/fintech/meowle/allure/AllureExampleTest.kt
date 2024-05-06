package ru.tinkoff.fintech.meowle.allure

import androidx.test.ext.junit.rules.activityScenarioRule
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Allure.step
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.PreferenceRule
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoSearchScreen

/**
 * @author Ruslan Ganeev
 */
@Epic("Fintech")
@Feature("Allure")
@RunWith(AllureAndroidJUnit4::class)
class AllureExampleTest {

    @get:Rule
    val prefs = PreferenceRule()

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    @Story("Report")
    @DisplayName("Allure test")
    fun allure() {
        val search = KaspressoSearchScreen()
        search.findCat("Саня")

        step("Проверяем список котов") {
            search.checkCatsListSize(4)
        }
    }
}