package ru.tinkoff.fintech.meowle.domain.repository

import ru.tinkoff.fintech.meowle.presentation.Mode

/**
 * @author blk
 */
interface SettingsRepository {

    suspend fun getAuth(): Boolean

    suspend fun setAuth(auth: Boolean)

    suspend fun getName(): String

    suspend fun setName(name: String)

    suspend fun getLaunchMode(): Mode

    suspend fun setLaunchMode(mode: Mode)

    fun getBaseUrl(): String?
}
