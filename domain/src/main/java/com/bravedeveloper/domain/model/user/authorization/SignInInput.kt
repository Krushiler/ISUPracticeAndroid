package com.bravedeveloper.domain.model.user.authorization

data class SignInInput(
    val phone: String,
    val password: String
)