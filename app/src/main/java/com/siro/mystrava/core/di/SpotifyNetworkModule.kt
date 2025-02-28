package com.siro.mystrava.core.di

import android.content.Context
import com.siro.mystrava.inf.Session
import com.siro.mystrava.inf.StravaSessionRepository
import com.siro.mystrava.inf.spotify.SpotifyApis
import com.siro.mystrava.inf.spotify.SpotifyCodeAuthInterceptor
import com.siro.mystrava.inf.spotify.SpotifySessionApi
import com.siro.mystrava.inf.spotify.SpotifySessionRepository
import com.siro.mystrava.inf.spotify.SpotifyTokenAuthenticator
import com.siro.mystrava.inf.spotify.SpotifyTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SpotifyNetworkModule {

    @Provides
    @Singleton
    fun providesSpotifySessionRepository(
        @ApplicationContext context: Context,
        spotifySessionApi: SpotifySessionApi
    ): SpotifySessionRepository =
        SpotifySessionRepository(context, spotifySessionApi)

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideSpotifySession(@Named("spotifyAuth") retrofit: Retrofit): SpotifySessionApi {
        return retrofit.create(SpotifySessionApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideSpotifyApis(@Named("spotifyApis") retrofit: Retrofit): SpotifyApis {
        return retrofit.create(SpotifyApis::class.java)
    }

    @Provides
    @Named("spotifyApis")
    @Reusable
    @JvmStatic
    internal fun provideSpotifyApiInterface(
        okHttpClient: OkHttpClient.Builder,
        spotifyTokenInterceptor: SpotifyTokenInterceptor,
        spotifyTokenAuthenticator: SpotifyTokenAuthenticator
    ): Retrofit {
        okHttpClient.addInterceptor(spotifyTokenInterceptor)
        okHttpClient.authenticator(spotifyTokenAuthenticator)

        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }


    @Provides
    @Named("spotifyAuth")
    @Reusable
    @JvmStatic
    internal fun provideSpotifyRetrofitInterface(
        okHttpClient: OkHttpClient.Builder,
        spotifyAuthInterceptor: SpotifyCodeAuthInterceptor
    ): Retrofit {
        okHttpClient.addInterceptor(spotifyAuthInterceptor)

        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }
}