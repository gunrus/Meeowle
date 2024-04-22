package ru.tinkoff.fintech.meowle.data.api.dto.request

/**
 * @author Ruslan Ganeev
 */
data class SearchRequestDto(
    val name: String,
    val order: String,
    val gender: String?,
)
