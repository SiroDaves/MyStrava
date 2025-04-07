package com.siro.mystrava.core.di

import android.content.Context
import androidx.work.Configuration
import com.siro.mystrava.domain.repositories.StravaDashboardRepository
import com.siro.mystrava.core.auth.StravaSessionRepository
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.remote.AthleteApi
import com.siro.mystrava.domain.repositories.SettingsRepo
import com.siro.mystrava.domain.repositories.SettingsRepoImpl
import com.siro.mystrava.presentation.widget.WidgetWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class] )
class AppModule {
    @Provides
    @Singleton
    fun provideDashboardRepository(
        @ApplicationContext context: Context,
        activitiesApi: ActivitiesApi
    ): StravaDashboardRepository =
        StravaDashboardRepository(
            context,
            activitiesApi
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRepoImpl: StravaSessionRepository,
        athleteApi: AthleteApi,
        @ApplicationContext context: Context,
    ): SettingsRepo =
        SettingsRepoImpl(settingsRepoImpl, athleteApi, context)

    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(
        widgetWorkerFactory: WidgetWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(widgetWorkerFactory)
            .build()
    }
}