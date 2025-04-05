package com.siro.mystrava.core.di

import android.content.Context
import com.siro.mystrava.BuildConfig
import com.siro.mystrava.core.auth.*
import com.siro.mystrava.domain.repositories.SessionRepository
import com.siro.mystrava.core.utils.ApiConstants
import com.siro.mystrava.data.sources.remote.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.*

@InstallIn(SingletonComponent::class)
@Module
@Suppress("unused")
object NetworkModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideSession(@Named("strava") retrofit: Retrofit): SessionApi {
        return retrofit.create(SessionApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivities(@Named("stravaApi") retrofit: Retrofit): ActivitiesApi {
        return retrofit.create(ActivitiesApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAthlete(@Named("stravaApi") retrofit: Retrofit): AthleteApi {
        return retrofit.create(AthleteApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSessionRepository(
        @ApplicationContext context: Context,
        api: SessionApi
    ): SessionRepository =
        SessionRepository(context, api)

    @Provides
    @Named("stravaApi")
    @Reusable
    @JvmStatic
    internal fun provideStravaApi(
        okHttpClient: OkHttpClient.Builder,
        authenticator: TokenAuthenticator,
        interceptor: AuthorizationInterceptor
    ): Retrofit {
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.authenticator(authenticator)

        return Retrofit.Builder()
            .baseUrl(ApiConstants.Uri.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Provides
    @Named("strava")
    @Reusable
    @JvmStatic
    internal fun provideStravaRetrofitInterface(okHttpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.Uri.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttp(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(logging)
        }
        return okHttpClient
    }
}