package ru.tinkoff.fintech.meowle.screens.kaspresso

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode
import ru.tinkoff.fintech.meowle.presentation.compose.ui.LazyListItemPosition
import ru.tinkoff.fintech.meowle.screens.kaspresso.element.SearchBarElement

/**
 * @author Ruslan Ganeev
 */
class KaspressoComposeSearchScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoComposeSearchScreen>(semanticsProvider) {

    val searchBar = SearchBarElement(semanticsProvider)

    private val catsList = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            hasTestTag("searchCatsList")
            useUnmergedTree = true
        },
        itemTypeBuilder = {
            itemType(::ComposeCatCard)
        },
        positionMatcher = { position -> SemanticsMatcher.expectValue(LazyListItemPosition, position) }
    )

    @OptIn(ExperimentalTestApi::class)
    fun checkFirstCatName(catName: String) {
        catsList.firstChild<ComposeCatCard> {
            name.assertTextContains(catName, true)
        }
    }
}

private class ComposeCatCard(
    semanticsNode: SemanticsNode,
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : KLazyListItemNode<ComposeCatCard>(semanticsNode, semanticsProvider) {
    val name: KNode = child {
        hasTestTag("catName")
        useUnmergedTree = true
    }
}
