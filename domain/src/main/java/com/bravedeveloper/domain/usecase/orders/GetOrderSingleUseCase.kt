package com.bravedeveloper.domain.usecase.orders

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.getorder.OrderData
import com.bravedeveloper.domain.model.city.order.getorder.OrderInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetOrderSingleUseCase(private val repository: OrdersRepository) : SingleUseCase<ResponseData<OrderData>> {

    private var input : OrderInput? = null

    fun saveInput(input: OrderInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<OrderData>>? {
        return input?.let { repository.getOrder(it) }
    }

}