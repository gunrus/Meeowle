package ru.tinkoff.fintech.meowle.domain

/**
 * @author Ruslan Ganeev
 */
sealed interface MeowleError

sealed interface BackendError : MeowleError {
    enum class Network : BackendError {
        UNKNOWN
    }

    sealed interface Business : BackendError {
        data class Unknown(val message: String) : Business
    }
}

enum class DatabaseError : MeowleError {
    UNKNOWN
}

enum class FileError : MeowleError {
    UNKNOWN
}
