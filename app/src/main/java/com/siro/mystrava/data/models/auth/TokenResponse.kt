package com.siro.mystrava.data.models.auth

import androidx.annotation.Keep

@Keep
data class TokenResponse(
    val access_token: String,
    val expires_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)