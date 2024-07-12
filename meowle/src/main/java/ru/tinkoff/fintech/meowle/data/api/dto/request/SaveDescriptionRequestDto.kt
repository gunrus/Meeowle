package ru.tinkoff.fintech.meowle.data.api.dto.request

/**
 * @author Ruslan Ganeev
 */
data class SaveDescriptionRequestDto(
    val catId: Long,
    val catDescription: String
)