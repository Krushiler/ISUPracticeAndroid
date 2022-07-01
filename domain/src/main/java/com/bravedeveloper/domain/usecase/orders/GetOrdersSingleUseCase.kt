package com.bravedeveloper.domain.usecase.orders

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.search.OrdersData
import com.bravedeveloper.domain.model.city.order.search.OrdersInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetOrdersSingleUseCase(
    private val repository: OrdersRepository
) : SingleUseCase<ResponseData<OrdersData>>{

    private var input : OrdersInput? = null

    fun saveInput(input : OrdersInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<OrdersData>>? {
        return input?.let { repository.getOrders(it) }
    }
}