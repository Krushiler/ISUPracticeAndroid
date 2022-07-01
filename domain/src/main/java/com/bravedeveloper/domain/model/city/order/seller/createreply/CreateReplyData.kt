package com.bravedeveloper.domain.model.city.order.seller.createreply

import com.bravedeveloper.domain.model.city.order.Order

data class CreateReplyData(
    val recordId: String?,
    val record: Order?
)