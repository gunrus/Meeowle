package ru.tinkoff.fintech.meowle.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import ru.tinkoff.fintech.meowle.presentation.Mode
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : SettingsRepository {

    private val meowlePrefs = context.getSharedPreferences("meowle", Context.MODE_PRIVATE)
    override suspend fun getAuth(): Boolean {
        return getBoolean("auth")
    }

    override suspend fun setAuth(auth: Boolean) {
        setBoolean("auth", auth)
    }

    override suspend fun getName(): String {
        return getString("name") ?: "Пользователь"
    }

    override suspend fun setName(name: String) {
        setString("name", name)
    }

    override suspend fun getLaunchMode(): Mode {
        return getString("launch_mode")?.let { Mode.valueOf(it) } ?: Mode.VIEWS
    }

    override suspend fun setLaunchMode(mode: Mode) {
        setString("launch_mode", mode.name)
    }

    override fun getBaseUrl(): String? {
        return getString("url")
    }

    private fun getString(paramName: String): String? {
        return meowlePrefs.getString(paramName, null)
    }

    private fun setString(paramName: String, value: String) {
        meowlePrefs.edit().putString(paramName, value).apply()
    }

    private fun getBoolean(paramName: String): Boolean {
        return meowlePrefs.getBoolean(paramName, false)
    }

    private fun setBoolean(paramName: String, value: Boolean) {
        meowlePrefs.edit().putBoolean(paramName, value).apply()
    }
}