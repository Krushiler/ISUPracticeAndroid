package com.bravedeveloper.domain.usecase.orders.customer

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersData
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class RemoveOrdersSingleUseCase(private val repository: OrdersRepository) :
    SingleUseCase<ResponseData<RemoveOrdersData>> {

    private var input: RemoveOrdersInput? = null

    fun saveInput(input: RemoveOrdersInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<RemoveOrdersData>>? {
        return input?.let { repository.removeOrders(it) }
    }
}