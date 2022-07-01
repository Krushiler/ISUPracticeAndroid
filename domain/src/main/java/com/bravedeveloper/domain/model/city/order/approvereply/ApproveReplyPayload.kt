package com.bravedeveloper.domain.model.city.order.approvereply

import com.bravedeveloper.domain.model.city.order.Order

data class ApproveReplyPayload(
    val recordId: String,
    val record: Order
)
