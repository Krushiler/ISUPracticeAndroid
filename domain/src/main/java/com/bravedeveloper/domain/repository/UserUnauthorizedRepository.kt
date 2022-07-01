package com.bravedeveloper.domain.repository

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.unauthorized.OrdersUnauthorizedData
import io.reactivex.Single

interface UserUnauthorizedRepository {
    fun getOrdersUnauthorized() : Single<ResponseData<OrdersUnauthorizedData>>
}