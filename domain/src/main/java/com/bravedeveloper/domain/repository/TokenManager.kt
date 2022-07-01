package com.bravedeveloper.domain.repository

interface TokenManager {
    fun saveToken(token: String)

    fun deleteToken()

    fun getToken()
}