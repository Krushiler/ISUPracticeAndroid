package com.bravedeveloper.domain.usecase.orders

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.unauthorized.OrdersUnauthorizedData
import com.bravedeveloper.domain.repository.UserUnauthorizedRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class OrdersUnauthorizedSingleUseCase(
    private val repository: UserUnauthorizedRepository
) : SingleUseCase<ResponseData<OrdersUnauthorizedData>> {
    override fun execute(): Single<ResponseData<OrdersUnauthorizedData>> {
        return repository.getOrdersUnauthorized()
    }
}