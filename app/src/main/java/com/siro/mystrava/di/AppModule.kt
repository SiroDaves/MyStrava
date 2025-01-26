package com.siro.mystrava.di

import android.content.Context
import androidx.work.Configuration
import com.siro.mystrava.ui.dashboard.StravaDashboardRepository
import com.siro.mystrava.inf.StravaSessionRepository
import com.siro.mystrava.inf.spotify.SpotifyApis
import com.siro.mystrava.strava.api.ActivitiesApi
import com.siro.mystrava.strava.api.AthleteApi
import com.siro.mystrava.ui.settings.SettingsRepo
import com.siro.mystrava.ui.settings.SettingsRepoImpl
import com.siro.mystrava.ui.spotifyjourney.SpotifyJourneyRepository
import com.siro.mystrava.ui.widget.WidgetWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [StravaNetworkModule::class, SpotifyNetworkModule::class] )
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
    fun provideSpotifyJourneyRepository(
        spotifyApis: SpotifyApis,
        stravaDashboardRepository: StravaDashboardRepository
    ): SpotifyJourneyRepository =
        SpotifyJourneyRepository(spotifyApis, stravaDashboardRepository)

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