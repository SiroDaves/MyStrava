package com.siro.mystrava.core.auth

import android.util.Log
import androidx.annotation.Keep
import com.siro.mystrava.domain.repositories.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

@Keep
class TokenAuthenticator @Inject constructor(val stravaSessionRepository: SessionRepository) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // This is a synchronous call
        val updatedToken = getNewToken()

        val newRequest = response.request.newBuilder()
            .removeHeader("Authorization")
            .header("Accept", "application/json")
            .header("Authorization", "Bearer $updatedToken")
            .build()

        Log.d("TAG", "authenticate: ${newRequest.headers.names().toString()}")

        return newRequest
    }

    private fun getNewToken(): String {
        return runBlocking {
            return@runBlocking stravaSessionRepository.refreshToken()
        }
    }
}