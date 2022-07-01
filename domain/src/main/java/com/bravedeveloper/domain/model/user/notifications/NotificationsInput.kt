package com.bravedeveloper.domain.model.user.notifications

import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class NotificationsInput(
    val sort: RepliesSortEnum,
    val filter: UserRoleEnum,
    val skip: Int,
    val take: Int
)