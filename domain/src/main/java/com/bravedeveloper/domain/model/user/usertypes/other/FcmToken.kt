package com.bravedeveloper.domain.model.user.usertypes.other

import com.bravedeveloper.domain.model.user.usertypes.ClientsUnion

data class FcmToken(
    val id: Int,
    val token: String,
    val createdAt: String,
    val updatedAt: String,
    val user: ClientsUnion
)
