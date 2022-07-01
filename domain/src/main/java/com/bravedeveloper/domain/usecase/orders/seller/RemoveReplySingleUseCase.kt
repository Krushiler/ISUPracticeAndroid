package com.bravedeveloper.domain.usecase.orders.seller

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyData
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class RemoveReplySingleUseCase(private val ordersRepository: OrdersRepository):
    SingleUseCase<ResponseData<RemoveReplyData>> {

    private var input : RemoveReplyInput? = null

    fun saveInput(input : RemoveReplyInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<RemoveReplyData>>? {
        return input?.let { ordersRepository.removeReply(it) }
    }
}