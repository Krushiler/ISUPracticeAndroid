package com.bravedeveloper.domain.model.user.registration

import com.bravedeveloper.domain.model.user.Me
import com.squareup.moshi.Json

data class SignUp(
    @Json(name = "record") val record: Me,
    @Json(name = "recordId") val recordId: String
)