package com.bravedeveloper.domain.usecase.token

import com.bravedeveloper.domain.repository.TokenManager

class SaveTokenUseCase(private val tokenManager: TokenManager) {
    fun execute(token: String) {
        tokenManager.saveToken(token)
    }
}