package ru.tinkoff.fintech.meowle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tinkoff.fintech.meowle.data.repository.CatsRepositoryImpl
import ru.tinkoff.fintech.meowle.data.repository.PhotosRepositoryImpl
import ru.tinkoff.fintech.meowle.data.repository.SettingsRepositoryImpl
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import ru.tinkoff.fintech.meowle.domain.repository.PhotosRepository
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import javax.inject.Singleton

/**
 * @author Ruslan Ganeev
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatRepository(catsRepositoryImpl: CatsRepositoryImpl): CatRepository

    @Binds
    @Singleton
    abstract fun bindPhotosRepository(photosRepositoryImpl: PhotosRepositoryImpl): PhotosRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
