package com.bravedeveloper.domain.model.city.order.seller.removereply

import com.bravedeveloper.domain.model.city.order.Order

data class RemoveReplyDataData(
    val recordId: String,
    val record: Order
)