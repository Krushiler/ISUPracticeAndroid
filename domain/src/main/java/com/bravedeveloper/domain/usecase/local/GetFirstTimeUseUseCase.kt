package com.bravedeveloper.domain.usecase.local

import com.bravedeveloper.domain.repository.FirstTimeUseManager

class GetFirstTimeUseUseCase(private val firstTimeUseManager: FirstTimeUseManager) {
    fun execute() = firstTimeUseManager.getFirstTimeUse()
}