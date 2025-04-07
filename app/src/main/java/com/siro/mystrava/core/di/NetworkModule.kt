package com.siro.mystrava.core.di

import android.content.Context
import com.siro.mystrava.BuildConfig
import com.siro.mystrava.core.auth.AuthorizationInterceptor
import com.siro.mystrava.core.auth.Session
import com.siro.mystrava.core.auth.StravaSessionRepository
import com.siro.mystrava.core.auth.TokenAuthenticator
import com.siro.mystrava.core.utils.ApiConstants
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.remote.AthleteApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideStravaSession(@Named("strava") retrofit: Retrofit): Session {
        return retrofit.create(Session::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivites(@Named("stravaApi") retrofit: Retrofit): ActivitiesApi {
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
        session: Session
    ): StravaSessionRepository =
        StravaSessionRepository(context, session)

    @Provides
    @Named("stravaApi")
    @Reusable
    @JvmStatic
    internal fun provideStravaApi(
        okHttpClient: OkHttpClient.Builder,
        authenticator: TokenAuthenticator,
        authorizationInterceptor: AuthorizationInterceptor
    ): Retrofit {
        okHttpClient.addInterceptor(authorizationInterceptor)
        okHttpClient.authenticator(authenticator)

        return Retrofit.Builder()
            .baseUrl(ApiConstants.Uri.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
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
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(logging)
        }
        return okHttpClient
    }
}