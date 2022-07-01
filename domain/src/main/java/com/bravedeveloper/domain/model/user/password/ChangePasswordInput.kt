package com.bravedeveloper.domain.model.user.password

data class ChangePasswordInput(
    val password: String,
    val newPassword: String,
    val confirmPassword: String
)


