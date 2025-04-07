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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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
    ): SessionRepository = SessionRepository(context, api)

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
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor { chain ->
                val request = chain.request()
                Timber.d("Request: ${request.method} ${request.url}")
                Timber.d("Headers: ${request.headers}")

                val response = chain.proceed(request)

                Timber.d("Response Code: ${response.code}")
                Timber.d("Response Headers: ${response.headers}")
                val responseBody = response.peekBody(Long.MAX_VALUE)
                Timber.d("Response Body: ${responseBody.string()}")

                response
            }
        }

        return okHttpClient
    }

}