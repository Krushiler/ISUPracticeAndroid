package com.bravedeveloper.domain.model

data class ResponseData<T>(
    val data: T?,
    val errors: List<Error>?
)
