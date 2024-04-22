package ru.tinkoff.fintech.meowle.domain

/**
 * @author Ruslan Ganeev
 */
sealed interface MeowleResult<out D, out E: MeowleError> {
    data class Success<out D, out E: MeowleError>(val data: D) : MeowleResult<D, E>
    data class Error<out D, out E: MeowleError>(val error: E) : MeowleResult<D, E>
}