package ru.tinkoff.fintech.meowle.screens.kaspresso

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

/**
 * @author Ruslan Ganeev
 */
class KaspressoSimpleComposeSearchScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoSimpleComposeSearchScreen>(semanticsProvider) {

    private val search = KNode(semanticsProvider) { hasTestTag("search") }
    private val searchOptions = onNode {
        hasTestTag("searchOptions")
        useUnmergedTree = true
    }

    fun findCat(catName: String) {
        search.performTextInput(catName)
        search.performImeAction()
    }
}