package com.bravedeveloper.domain.model.user.authorization

import com.bravedeveloper.domain.model.user.authorization.SignIn
import com.squareup.moshi.Json

data class SignInData (

    @Json(name = "signIn") var signIn : SignIn

)