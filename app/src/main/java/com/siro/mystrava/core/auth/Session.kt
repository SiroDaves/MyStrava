package com.siro.mystrava.core.auth

import androidx.annotation.Keep
import com.siro.mystrava.data.models.auth.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@Keep
interface Session {
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