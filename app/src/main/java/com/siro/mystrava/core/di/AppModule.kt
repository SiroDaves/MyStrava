package com.siro.mystrava.core.di

import android.content.Context
import com.siro.mystrava.domain.repositories.*
import com.siro.mystrava.data.sources.remote.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class] )
class AppModule {
    @Provides
    @Singleton
    fun provideHomeRepository(
        @ApplicationContext context: Context,
        activitiesApi: ActivitiesApi
    ): HomeRepository =
        HomeRepository(
            context,
            activitiesApi
        )

    @Provides
    @Singleton
    fun provideWorkoutRepository(
        @ApplicationContext context: Context,
        activitiesApi: ActivitiesApi
    ): WorkoutRepository =
        WorkoutRepository(
            context,
            activitiesApi
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRepoImpl: SessionRepository,
        athleteApi: AthleteApi,
        @ApplicationContext context: Context,
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsRepoImpl, athleteApi, context)
}