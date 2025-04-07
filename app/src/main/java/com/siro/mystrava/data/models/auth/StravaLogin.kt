package com.siro.mystrava.data.models.auth

import android.content.Context
import androidx.annotation.Keep
import com.siro.mystrava.core.utils.ApiConstants

@Keep
class StravaLogin(private val context: Context?) {
    private var clientID = 0
    private var redirectURI: String? = null
    private var approvalPrompt: String? = null
    private var accessScope: String? = null

    fun withClientID(clientID: Int): StravaLogin {
        this.clientID = clientID
        return this
    }

    fun withRedirectURI(redirectURI: String?): StravaLogin {
        this.redirectURI = redirectURI
        return this
    }

    fun withApprovalPrompt(approvalPrompt: String?): StravaLogin {
        this.approvalPrompt = approvalPrompt
        return this
    }

    fun withAccessScope(accessScope: String?): StravaLogin {
        this.accessScope = accessScope
        return this
    }

    fun makeLoginURL(): String {
        val loginURLBuilder = StringBuilder()
        loginURLBuilder.append(ApiConstants.Uri.AUTH)
        loginURLBuilder.append(clientIDParameter())
        loginURLBuilder.append(redirectURIParameter())
        loginURLBuilder.append(approvalPromptParameter())
        loginURLBuilder.append(accessScopeParameter())
        return loginURLBuilder.toString()
    }

    private fun clientIDParameter(): String {
        return "&client_id=$clientID"
    }

    private fun redirectURIParameter(): String {
        return if (redirectURI != null) {
            "&redirect_uri=$redirectURI"
        } else {
            ""
        }
    }

    private fun approvalPromptParameter(): String {
        return if (approvalPrompt != null) {
            "&approval_prompt=" + approvalPrompt.toString()
        } else {
            ""
        }
    }

    private fun accessScopeParameter(): String {
        return if (accessScope != null) {
            "&scope=" + accessScope.toString()
        } else {
            ""
        }
    }

    companion object {
        fun withContext(context: Context?): StravaLogin {
            return StravaLogin(context)
        }
    }
}