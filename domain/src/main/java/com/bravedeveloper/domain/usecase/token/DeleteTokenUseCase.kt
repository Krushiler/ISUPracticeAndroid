package com.bravedeveloper.domain.usecase.token

import com.bravedeveloper.domain.repository.TokenManager

class DeleteTokenUseCase(private val tokenManager: TokenManager) {
    fun execute() {
        tokenManager.deleteToken()
    }
}