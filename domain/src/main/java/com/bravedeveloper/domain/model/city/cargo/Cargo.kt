package com.bravedeveloper.domain.model.city.cargo

data class Cargo(
    val id: String,
    val type: String,
    val positionNumber: Int,
    val subtypes: List<CargoSubType>
)
