package com.siro.mystrava.core.di

import android.content.Context
import androidx.work.Configuration
import com.siro.mystrava.data.sources.remote.*
import com.siro.mystrava.domain.repositories.*
import com.siro.mystrava.presentation.widgets.WidgetWorkerFactory
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
    fun provideHomeRepo(
        @ApplicationContext context: Context,
        api: ActivitiesApi
    ): HomeRepository = HomeRepository( context, api )

    @Provides
    @Singleton
    fun provideSettingsRepo(
        settingsRepoImpl: SessionRepository,
        api: AthleteApi,
        @ApplicationContext context: Context,
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsRepoImpl, api, context)

    @Singleton
    @Provides
    fun provideWorkManagerConfig(
        widgetWorkerFactory: WidgetWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(widgetWorkerFactory)
            .build()
    }
}