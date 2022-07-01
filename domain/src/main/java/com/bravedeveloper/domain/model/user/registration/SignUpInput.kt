package com.bravedeveloper.domain.model.user.registration

import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.squareup.moshi.Json

data class SignUpInput(
    @Json(name = "name") val name: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "role") val role: UserRoleEnum,
    @Json(name = "cityId") val cityId: Int,
    @Json(name = "acceptPrivacy") val acceptPrivacy: Boolean,
    @Json(name = "shouldSendCode") val shouldSendCode: Boolean
)