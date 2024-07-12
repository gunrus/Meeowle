package ru.tinkoff.fintech.meowle.data.api.dto.response

import ru.tinkoff.fintech.meowle.data.api.dto.CatDto

/**
 * @author Ruslan Ganeev
 */
data class RatingResponseDto(
    val likes: List<CatDto>,
    val dislikes: List<CatDto>
)
