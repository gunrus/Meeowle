package ru.tinkoff.fintech.meowle.testing.mock.response

import ru.tinkoff.fintech.meowle.data.api.dto.CatDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.CatByIdResponseDto

/**
 * @author Ruslan Ganeev
 */
object DetailsResponseFactory {

    fun catDetails(): CatByIdResponseDto {
        return CatByIdResponseDto(
            cat = CatDto(
                id = 1,
                name = "Пушок",
                description = "Белый пушистый кот",
                tags = "",
                gender = "male",
                likes = 1,
                dislikes = 2
            )
        )
    }
}
