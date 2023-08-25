package de.skash.timetrack.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.skash.timetrack.core.repository.ApiAuthRepository
import de.skash.timetrack.core.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository {
        return ApiAuthRepository()
    }
}