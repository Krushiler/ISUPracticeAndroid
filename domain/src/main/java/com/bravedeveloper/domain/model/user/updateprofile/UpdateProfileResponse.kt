package com.bravedeveloper.domain.model.user.updateprofile

import com.squareup.moshi.Json

data class UpdateProfileResponse(
    @Json(name="data") val data: UpdateProfileData
)
