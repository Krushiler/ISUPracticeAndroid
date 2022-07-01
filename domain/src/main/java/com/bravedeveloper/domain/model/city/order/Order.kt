package com.bravedeveloper.domain.model.city.order

import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.city.hours.ReceivingHoursEnum
import com.bravedeveloper.domain.model.user.usertypes.CustomerType
import com.bravedeveloper.domain.model.user.usertypes.SellerType
import com.bravedeveloper.domain.model.user.usertypes.other.Reply

data class Order(
    val id: String?,
    val contact: String?,
    val phone: String?,
    val destination: String?,
    val coords: List<Double>?,
    val cargoType: String?,
    val cargoSubtype: String?,
    val cargoVolume: String?,
    val measure: String?,
    val date: String?,
    val time: ReceivingHoursEnum?,
    val status: OrderStatusEnum?,
    val price: Int?,
    val comment: String?,
    val number: Int?,
    val customer: CustomerType?,
    val seller: SellerType?,
    val city: City?,
    val region: Region?,
    val replies: List<Reply>?,
    val views: Int?,
    val deletedAt: String?,
    val createdAt: String?,
    val updatedAt: String?
) {

}