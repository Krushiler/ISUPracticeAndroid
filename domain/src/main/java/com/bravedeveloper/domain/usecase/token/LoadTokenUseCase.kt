package com.bravedeveloper.domain.usecase.token

import com.bravedeveloper.domain.repository.TokenManager

class LoadTokenUseCase(private val tokenManager: TokenManager) {
    fun execute() {
        tokenManager.getToken()
    }
}