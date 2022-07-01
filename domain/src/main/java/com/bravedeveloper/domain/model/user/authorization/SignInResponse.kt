package com.bravedeveloper.domain.model.user.authorization

import com.squareup.moshi.Json

data class SignInResponse (

    @Json(name="data") var data : SignInData

)