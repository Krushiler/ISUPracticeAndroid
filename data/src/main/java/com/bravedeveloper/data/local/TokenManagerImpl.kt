package com.bravedeveloper.data.local

import android.content.SharedPreferences
import com.bravedeveloper.data.remote.api.request.Token
import com.bravedeveloper.data.remote.api.util.Constants
import com.bravedeveloper.domain.repository.TokenManager

class TokenManagerImpl(private val appPreferences: SharedPreferences) : TokenManager {
    override fun saveToken(token: String) {
        Token.token = token
        val tokenEditor = appPreferences.edit()
        tokenEditor.putString(Constants.TOKEN_KEY, token)
        tokenEditor.apply()
    }

    override fun deleteToken() {
        Token.token = ""
        val tokenEditor = appPreferences.edit()
        tokenEditor.remove(Constants.TOKEN_KEY)
        tokenEditor.apply()
    }

    override fun getToken() {
        Token.token = appPreferences.getString(Constants.TOKEN_KEY, "").orEmpty()
    }
}