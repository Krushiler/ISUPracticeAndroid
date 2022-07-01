package com.bravedeveloper.domain.model

import com.squareup.moshi.Json

data class Error(
    @Json(name = "message") val message: Any
)
