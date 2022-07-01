package com.bravedeveloper.domain.usecase.local

import com.bravedeveloper.domain.repository.FirstTimeUseManager

class SetFirstTimeUseUseCase(private val firstTimeUseManager: FirstTimeUseManager) {
    fun execute(firstTime: Boolean) {
        firstTimeUseManager.setFirstTimeUse(firstTime)
    }
}