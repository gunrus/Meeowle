package ru.tinkoff.fintech.meowle

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.testing.rule.MeowleTestRule

/**
 * @author Ruslan Ganeev
 */
class ComposeSimpleExample {

    @get:Rule
    val testRule = MeowleTestRule(isCompose = true)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun search() {
        composeTestRule.onNodeWithContentDescription("Поиск").assertIsDisplayed()

        composeTestRule.onNode(hasContentDescription("Поиск")).assertIsDisplayed()

        val searchInput = composeTestRule.onNodeWithTag("search")
        searchInput.performTextInput("Саня")
        searchInput.assertTextEquals("Саня")
        searchInput.performImeAction()

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("catCard"), 3_000)

        composeTestRule
            .onAllNodesWithTag("catName", true)
            .onFirst()
            .assertTextContains("Саня", true)
    }
}