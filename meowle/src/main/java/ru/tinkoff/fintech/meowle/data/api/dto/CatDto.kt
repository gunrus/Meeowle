package ru.tinkoff.fintech.meowle.data.api.dto

import kotlinx.serialization.Serializable

/**
 * @author Ruslan Ganeev
 */
@Serializable
data class CatDto(
    val id: Long,
    val name: String,
    val description: String?,
    val tags: String,
    val gender: String,
    val likes: Int,
    val dislikes: Int
)
