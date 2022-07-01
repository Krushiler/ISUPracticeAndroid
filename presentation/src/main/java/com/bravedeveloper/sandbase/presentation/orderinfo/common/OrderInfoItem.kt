package com.bravedeveloper.sandbase.presentation.orderinfo.common

import androidx.annotation.StringRes

data class OrderInfoItem<T>(
    @StringRes val title: Int,
    val description: T,
    val type: OrderInfoItemType
)