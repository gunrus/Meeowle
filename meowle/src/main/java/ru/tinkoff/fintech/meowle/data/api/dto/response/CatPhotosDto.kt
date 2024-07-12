package ru.tinkoff.fintech.meowle.data.api.dto.response

import kotlinx.serialization.Serializable

/**
 * @author Ruslan Ganeev
 */
@Serializable
data class CatPhotosDto(
    val images: List<String>
)
