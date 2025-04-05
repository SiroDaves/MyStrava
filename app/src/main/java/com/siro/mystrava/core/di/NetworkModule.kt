package com.siro.mystrava.core.di

import android.content.Context
import com.siro.mystrava.BuildConfig
import com.siro.mystrava.inf.AuthorizationInterceptor
import com.siro.mystrava.inf.Session
import com.siro.mystrava.inf.StravaSessionRepository
import com.siro.mystrava.inf.TokenAuthenticator
import com.siro.mystrava.data.sources.remote.ActivitiesApi
import com.siro.mystrava.data.sources.remote.AthleteApi
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

    /**
     * a strava api makes the calls to the api and attaches the token to the header with an okhttp interceptor from the session. Session should have a
     * getter method that checks the expiration and automatically gets a new token if needed. Session
     */

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
            .baseUrl("https://www.strava.com/api/v3/")
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
            .baseUrl("https://www.strava.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttp(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        //okhttp3.logging.HttpLoggingInterceptor.setLevel = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(logging)
        }
        return okHttpClient
    }
}