package com.siro.mystrava.core.auth

import android.util.Log
import androidx.annotation.Keep
import com.siro.mystrava.domain.repositories.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@Keep
class AuthorizationInterceptor @Inject constructor(val sessionRepo: SessionRepository) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (sessionRepo.getExpiration() > System.currentTimeMillis()) {
            runBlocking {
                sessionRepo.refreshToken()
            }
        }

        val token = sessionRepo.getAccessToken()
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer $token")
            .method(original.method(), original.body())
        val request = requestBuilder.build()
        Log.d("TAG", "Authorization: Bearer $token")
        return chain.proceed(request)
    }
}