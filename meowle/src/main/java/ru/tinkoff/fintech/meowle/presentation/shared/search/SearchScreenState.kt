package ru.tinkoff.fintech.meowle.presentation.shared.search

import ru.tinkoff.fintech.meowle.domain.cat.Cat

/**
 * @author Ruslan Ganeev
 */
data class SearchScreenState(
    val status: Status = Status.INITIAL,
    val cats: List<Cat> = emptyList(),
    val isBottomSheetExpanded: Boolean = false,
)
