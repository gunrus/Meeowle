package ru.tinkoff.fintech.meowle.presentation.shared.rating

import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Vote

/**
 * @author Ruslan Ganeev
 */
data class RatingState(
    val vote: Vote = Vote.LIKES,
    val cats: List<Cat> = emptyList()
)
