package com.bravedeveloper.domain.model.user.notifications

import com.bravedeveloper.domain.model.city.order.search.PaginationInfo

data class RepliesData(
    val items: ReplyItems,
    val pageInfo: PaginationInfo
)