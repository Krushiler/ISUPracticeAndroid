package com.bravedeveloper.sandbase.presentation.orderList.base

fun interface OnOrderListItemClick<T> {
    fun onClick(item: T)
}