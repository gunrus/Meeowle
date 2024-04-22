package ru.tinkoff.fintech.meowle.data.api.dto.request

/**
 * @author Ruslan Ganeev
 */
data class VoteRequestDto(
    val like: Boolean,
    val dislike: Boolean
)