package ru.tinkoff.fintech.meowle.data.api.dto.response

import ru.tinkoff.fintech.meowle.data.api.dto.CatDto

/**
 * @author Ruslan Ganeev
 */
data class SearchResponseDto(
    val groups: List<LetterGroup>,
)

data class LetterGroup(
    val title: String,
    val cats: List<CatDto>,
    val count: Int
)
