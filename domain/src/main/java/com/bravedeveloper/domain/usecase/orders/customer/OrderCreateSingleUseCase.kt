package com.bravedeveloper.domain.usecase.orders.customer

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateData
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class OrderCreateSingleUseCase(private val repository: OrdersRepository) :
    SingleUseCase<ResponseData<OrderCreateData>> {

    private var input: OrderCreateInput? = null

    fun saveInput(input: OrderCreateInput, onNext: () -> Unit) {
        this.input = input
        onNext()
    }

    override fun execute(): Single<ResponseData<OrderCreateData>>? {
        return input?.let { repository.orderCreate(it) }
    }

}