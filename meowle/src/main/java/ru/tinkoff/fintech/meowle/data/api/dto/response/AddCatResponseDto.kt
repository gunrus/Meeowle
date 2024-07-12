package ru.tinkoff.fintech.meowle.data.api.dto.response

import ru.tinkoff.fintech.meowle.data.api.dto.CatDto

/**
 * @author Ruslan Ganeev
 */
data class AddCatResponseDto(
    val cats: List<CatsResponseDto>
)

data class CatsResponseDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val tags: String?,
    val gender: String?,
    val likes: Int?,
    val dislikes: Int?,
    val cat: CatDto?,
    val errorDescription: String?
)
