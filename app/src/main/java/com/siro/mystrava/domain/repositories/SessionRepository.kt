package com.siro.mystrava.domain.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Keep
import com.siro.mystrava.BuildConfig.*
import com.siro.mystrava.R
import com.siro.mystrava.core.auth.ISessionRepository
import com.siro.mystrava.core.auth.Session
import com.siro.mystrava.data.models.auth.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Keep
class SessionRepository @Inject constructor(
    val context: Context,
    private val session: Session
) : ISessionRepository {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    override suspend fun getFirstTokens(code: String): TokenResponse {
        val firstToken: TokenResponse
        withContext(context = Dispatchers.IO) {
            firstToken = session.getFirstToken(
                STRAVA_CLIENT_ID,
                STRAVA_CLIENT_SECRET,
                code,
                "authorization_code",
            )
            setAccessToken(firstToken.access_token)
            setRefreshToken(firstToken.refresh_token)
            firstToken
        }

        return firstToken
    }

    override suspend fun refreshToken() : String {
        return withContext(context = Dispatchers.IO) {
            val newTokens = session.refreshToken(
                STRAVA_CLIENT_ID,
                STRAVA_CLIENT_SECRET,
                getRefreshToken(),
                "refresh_token",
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