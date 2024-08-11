package ru.tinkoff.fintech.meowle.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.tinkoff.fintech.meowle.domain.repository.LocalStorage
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import ru.tinkoff.fintech.meowle.presentation.Mode
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : SettingsRepository {

    private val storage = LocalStorage(context)

    override suspend fun getAuth(): Boolean {
        return storage.getBoolean("auth")
    }

    override suspend fun setAuth(auth: Boolean) {
        storage.setBoolean("auth", auth)
    }

    override suspend fun getName(): String {
        return storage.getString("name") ?: "Пользователь"
    }

    override suspend fun setName(name: String) {
        storage.setString("name", name)
    }

    override suspend fun getLaunchMode(): Mode {
        return storage.getString("launch_mode")?.let { Mode.valueOf(it) } ?: Mode.VIEWS
    }

    override suspend fun setLaunchMode(mode: Mode) {
        storage.setString("launch_mode", mode.name)
    }

    override fun getBaseUrl(): String {
        return storage.getString("url") ?: "https://meowle.fintech-qa.ru"
    }
}
