package com.bravedeveloper.sandbase.presentation.orderList.seller.neworders

import com.bravedeveloper.domain.model.user.usertypes.CustomerType

data class OrderItemNew(
    var id: String?,
    var title: String?,
    var location: String?,
    var dateAndTime: String?,
    var commentary: String?,
    val number: Int?,
    val customer: CustomerType?
)