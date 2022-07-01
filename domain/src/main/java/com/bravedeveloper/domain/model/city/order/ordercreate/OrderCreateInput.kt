package com.bravedeveloper.domain.model.city.order.ordercreate

import com.bravedeveloper.domain.model.city.hours.ReceivingHoursEnum

data class OrderCreateInput(
    val coords: Pair<Double, Double>,
    val contact: String,
    val phone: String,
    val destination: String,
    val cargoType: String,
    val cargoSubtype: String,
    val cargoVolume: String,
    val date: String,
    val time: ReceivingHoursEnum,
    val comment: String,
    val measure: String
)
