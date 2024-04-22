package ru.tinkoff.fintech.meowle.screens.uiautomator

import androidx.test.uiautomator.By
import org.junit.Assert.assertEquals
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class UiAutomatorSearchScreen : BaseUiAutomatorScreen() {

    private val searchTitle = By.res("ru.tinkoff.fintech.meowle", "tw_search_title")

    fun checkTitle() {
        val actualTitleText = waitFindObject(searchTitle).text
        val expectedTitleText = resourceString(R.string.search_title)
        assertEquals(expectedTitleText, actualTitleText)
    }
}