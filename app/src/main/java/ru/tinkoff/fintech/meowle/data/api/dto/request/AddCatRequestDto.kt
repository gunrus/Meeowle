package ru.tinkoff.fintech.meowle.data.api.dto.request

/**
 * @author Ruslan Ganeev
 */
data class AddCatRequestDto(
    val cats: List<AddCatDto>
)

data class AddCatDto(
    val name: String,
    val gender: String,
    val description: String
)
