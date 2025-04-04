package com.siro.mystrava.core.di

import android.content.Context
import androidx.work.Configuration
import com.siro.mystrava.domain.DashboardRepository
import com.siro.mystrava.data.repositories.SessionRepository
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.remote.AthleteApi
import com.siro.mystrava.presentation.settings.*
import com.siro.mystrava.presentation.widget.WidgetWorkerFactory
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
    fun provideDashboardRepository(
        @ApplicationContext context: Context,
        activitiesApi: ActivitiesApi
    ): DashboardRepository =
        DashboardRepository(
            context,
            activitiesApi
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRepoImpl: SessionRepository,
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