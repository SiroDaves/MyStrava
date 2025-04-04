package com.siro.mystrava.data.sources.remote

import androidx.annotation.Keep
import com.siro.mystrava.data.models.user.TokenResponse
import retrofit2.http.*

@Keep
interface SessionApi {
    @POST("/oauth/token")
    @FormUrlEncoded
    suspend fun getFirstToken(
        @Field("client_id") clientID: Int,
        @Field("client_secret") clientSecret: String?,
        @Field("code") code: String?,
        @Field("grant_type") grantType: String
    ): TokenResponse

    @POST("/oauth/token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("client_id") clientID: Int,
        @Field("client_secret") clientSecret: String?,
        @Field("refresh_token") refreshToken: String?,
        @Field("grant_type") grantType: String
    ): TokenResponse

}