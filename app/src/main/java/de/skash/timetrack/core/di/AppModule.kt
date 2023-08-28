package de.skash.timetrack.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.skash.timetrack.core.repository.ApiAuthRepository
import de.skash.timetrack.core.repository.AuthRepository
import de.skash.timetrack.core.repository.RealmSearchHistoryRepository
import de.skash.timetrack.core.repository.SearchHistoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository {
        return ApiAuthRepository()
    }

    @Singleton
    @Provides
    fun provideSearchHistory(): SearchHistoryRepository {
        return RealmSearchHistoryRepository()
    }
}