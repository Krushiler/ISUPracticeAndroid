package com.bravedeveloper.domain.model.user.registration

import com.squareup.moshi.Json

data class SignUpData(
    @Json(name = "signUp") val signUp: SignUp
)