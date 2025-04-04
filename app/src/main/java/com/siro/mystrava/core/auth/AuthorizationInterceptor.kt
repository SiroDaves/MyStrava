package com.siro.mystrava.core.auth

import androidx.annotation.Keep
import com.siro.mystrava.data.repositories.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@Keep
class AuthorizationInterceptor @Inject constructor(val stravaSessionRepository: SessionRepository) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (stravaSessionRepository.getExpiration() > System.currentTimeMillis()) {
            runBlocking {
                stravaSessionRepository.refreshToken()
            }
        }

        val token = stravaSessionRepository.getAccessToken()
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer $token")
            .method(original.method, original.body)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}