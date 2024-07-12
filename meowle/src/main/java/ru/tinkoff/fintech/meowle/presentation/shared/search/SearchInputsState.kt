package ru.tinkoff.fintech.meowle.presentation.shared.search

import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption

/**
 * @author Ruslan Ganeev
 */
data class SearchInputsState(
    val searchText: String = "",
    val searchOrder: OrderOption = OrderOption.ASC,
    val searchGender: GenderOption = GenderOption.ALL,
    val isSearchTextValid: Boolean = true,
)
