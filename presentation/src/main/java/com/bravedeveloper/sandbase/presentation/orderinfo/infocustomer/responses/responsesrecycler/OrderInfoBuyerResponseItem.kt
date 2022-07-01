package com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.responsesrecycler

import com.bravedeveloper.domain.model.city.order.OrderStatusEnum

data class OrderInfoBuyerResponseItem (
    val id : String,
    val imageSource : String?,
    val nameAndSurname : String,
    val rating : Int?,
    val isCheckedSeller : Boolean,
    val isCashPayment : Boolean,
    val isCashlessPayment : Boolean,
    val commentary : String?,
    val price : String,
    val phone : String,
    val isAffordedResponse : Boolean,
    val orderStatus: OrderStatusEnum?,
    val orderId: String
    )