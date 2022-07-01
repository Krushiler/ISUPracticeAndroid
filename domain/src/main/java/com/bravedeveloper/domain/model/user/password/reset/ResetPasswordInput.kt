package com.bravedeveloper.domain.model.user.password.reset

data class ResetPasswordInput(
    val phone: String,
    val justTimeLeft: Boolean
)