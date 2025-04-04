package com.siro.mystrava.data.repositories

import android.content.*
import androidx.annotation.Keep
import com.siro.mystrava.BuildConfig.*
import com.siro.mystrava.R
import com.siro.mystrava.data.models.user.*
import com.siro.mystrava.data.sources.remote.SessionApi
import kotlinx.coroutines.*
import javax.inject.Inject

@Keep
class SessionRepository @Inject constructor(
    val context: Context,
    private val api: SessionApi
) : ISessionRepository {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    override suspend fun getFirstTokens(code: String): TokenResponse {
        val firstToken: TokenResponse
        withContext(context = Dispatchers.IO) {
            firstToken = api.getFirstToken(
                STRAVA_CLIENT_ID,
                STRAVA_CLIENT_SECRET,
                code,
                GrantType.AUTHORIZATION_CODE.toString()
            )
            setAccessToken(firstToken.access_token)
            setRefreshToken(firstToken.refresh_token)
            firstToken
        }

        return firstToken
    }

    override suspend fun refreshToken() : String {
        return withContext(context = Dispatchers.IO) {
            val newTokens = api.refreshToken(
                STRAVA_CLIENT_ID,
                STRAVA_CLIENT_SECRET,
                getRefreshToken(),
                GrantType.REFRESH_TOKEN.toString()
            )

            setAccessToken(newTokens.access_token)
            setRefreshToken(newTokens.refresh_token)
            newTokens.access_token
        }
    }

    override fun setAccessToken(accessToken: String) {
        with(preferences.edit()) {
            putString(ACCESS_TOKEN, accessToken)
            commit()
        }
    }

    override fun getAccessToken(): String {
        return preferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    override fun setRefreshToken(refreshToken: String) {
        with(preferences.edit()) {
            putString(REFRESH_TOKEN, refreshToken)
            commit()
        }
    }

    override fun getRefreshToken(): String {
        return preferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    override fun setExpiration(expiration: Int) {
        with(preferences.edit()) {
            putInt(EXPIRATION, expiration)
            commit()
        }
    }

    override fun getExpiration(): Int {
        return preferences.getInt(EXPIRATION, 0)
    }

    fun isLoggedIn (): Boolean {
        val doesHaveToken = !preferences.getString(ACCESS_TOKEN, "").isNullOrEmpty()
        val isTokenValid = getExpiration() < System.currentTimeMillis()

        return doesHaveToken && isTokenValid
    }

    fun logOff (){
        preferences.edit().remove(ACCESS_TOKEN).apply()
        preferences.edit().remove(REFRESH_TOKEN).apply()
    }

    @Keep
    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val EXPIRATION = "EXPIRATION"
    }

}