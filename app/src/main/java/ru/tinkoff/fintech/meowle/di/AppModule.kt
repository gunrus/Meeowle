package ru.tinkoff.fintech.meowle.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.tinkoff.fintech.meowle.data.api.CatsApi
import ru.tinkoff.fintech.meowle.data.database.CatsDatabase
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ruslan Ganeev
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val BASE_URL = "https://meowle.fintech-qa.ru"

    @Provides
    @Singleton
    fun provideCatsApi(
        settingsRepository: SettingsRepository
    ): CatsApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

        val moshiConverterFactory = MoshiConverterFactory
            .create()
            .withNullSerialization()

        val url = settingsRepository.getBaseUrl() ?: BASE_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        CatsDatabase::class.java,
        "cats_database"
    ).build()

    @Singleton
    @Provides
    fun provideCatDao(db: CatsDatabase) = db.catsDao()
}
