package com.bravedeveloper.domain.model.user.authorization

import com.bravedeveloper.domain.model.user.Me
import com.squareup.moshi.Json

class SignIn(
    @Json(name="token") var token: String,
    @Json(name="me") var me: Me?
)
