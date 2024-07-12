package ru.tinkoff.fintech.meowle.data.api.dto.response

import kotlinx.serialization.Serializable
import ru.tinkoff.fintech.meowle.data.api.dto.CatDto

/**
 * @author Ruslan Ganeev
 */
@Serializable
data class CatByIdResponseDto(
    val cat: CatDto
)
