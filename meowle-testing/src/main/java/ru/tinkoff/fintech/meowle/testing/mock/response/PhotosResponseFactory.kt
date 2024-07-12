package ru.tinkoff.fintech.meowle.testing.mock.response

import ru.tinkoff.fintech.meowle.data.api.dto.response.CatPhotosDto

/**
 * @author Ruslan Ganeev
 */
object PhotosResponseFactory {

    fun photos(): CatPhotosDto {
        return CatPhotosDto(
            images = listOf()
        )
    }
}