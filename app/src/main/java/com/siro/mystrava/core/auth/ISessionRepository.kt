package com.siro.mystrava.core.auth

import androidx.annotation.Keep
import com.siro.mystrava.data.models.auth.TokenResponse

@Keep
interface ISessionRepository {
    suspend fun getFirstTokens(code: String): TokenResponse
    suspend fun refreshToken(): String

    fun setAccessToken(accessToken: String)
    fun getAccessToken(): String

    fun setRefreshToken(refreshToken: String)
    fun getRefreshToken(): String

    fun setExpiration(expiration: Int)
    fun getExpiration(): Int
}