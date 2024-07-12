package ru.tinkoff.fintech.meowle.data.api.dto.response

import kotlinx.serialization.Serializable
import ru.tinkoff.fintech.meowle.data.api.dto.CatDto

/**
 * @author Ruslan Ganeev
 */
@Serializable
data class SearchResponseDto(
    val groups: List<LetterGroup>,
)

@Serializable
data class LetterGroup(
    val title: String,
    val cats: List<CatDto>,
    val count: Int
)
