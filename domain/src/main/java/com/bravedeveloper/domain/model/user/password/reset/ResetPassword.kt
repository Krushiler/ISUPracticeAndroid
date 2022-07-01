package com.bravedeveloper.domain.model.user.password.reset

data class ResetPassword(
    val isNew: Boolean,
    val timeUntilNew: Double
)