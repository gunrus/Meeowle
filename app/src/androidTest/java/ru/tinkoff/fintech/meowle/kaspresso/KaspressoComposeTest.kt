package ru.tinkoff.fintech.meowle.kaspresso

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.PreferenceRule
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoComposeSearchScreen

/**
 * @author Ruslan Ganeev
 */
class KaspressoComposeTest  : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val prefs = PreferenceRule()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun searchCats() = run {
        val searchScreen = KaspressoComposeSearchScreen(composeTestRule)

        searchScreen.searchBar.findCat("Саня")

        searchScreen.checkFirstCatName("Саня")
    }
}