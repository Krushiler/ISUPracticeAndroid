package com.bravedeveloper.domain.model.city

data class CitySuggestionsData(
    val substring: String,
    val take: Int = 25
)
