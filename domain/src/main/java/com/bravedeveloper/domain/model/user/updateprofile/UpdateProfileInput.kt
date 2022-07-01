package com.bravedeveloper.domain.model.user.updateprofile

data class UpdateProfileInput(
    val name: String,
    val email: String,
    val cityId: Int
)
