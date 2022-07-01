package com.bravedeveloper.domain.model.user.registration

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "data") val data: SignUpData
)