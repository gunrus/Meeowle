package ru.tinkoff.fintech.meowle.screens.kaspresso.element

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

/**
 * @author Ruslan Ganeev
 */
class SearchBarElement(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<SearchBarElement>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag("search") }
) {
    private val searchInput = KNode(semanticsProvider) { hasTestTag("search") }
    private val searchOptions: KNode = child {
        hasTestTag("searchOptions")
        useUnmergedTree = true
    }

    fun findCat(catName: String) {
        searchInput.performTextInput(catName)
        searchInput.performImeAction()
    }

    fun clickOnSearchOptions() {
        searchOptions.performClick()
    }
}