package ru.tinkoff.fintech.meowle.presentation.view.fragments.rv_helpers.search

import ru.tinkoff.fintech.meowle.domain.cat.Cat

sealed class SearchCatListItem (
    val type: Int
) {
    companion object {
        const val TYPE_CAT = 0
        const val TYPE_DIVIDER = 1
    }
}

class DataItemSearch(val catItem: Cat) : SearchCatListItem(TYPE_CAT)
class DividerItemSearch(var letter: String) : SearchCatListItem(TYPE_DIVIDER)

