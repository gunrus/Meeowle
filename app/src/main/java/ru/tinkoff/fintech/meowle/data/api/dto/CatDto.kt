package ru.tinkoff.fintech.meowle.data.api.dto

/**
 * @author Ruslan Ganeev
 */
data class CatDto(
    val id: Long,
    val name: String,
    val description: String?,
    val tags: String,
    val gender: String,
    val likes: Int,
    val dislikes: Int
)
