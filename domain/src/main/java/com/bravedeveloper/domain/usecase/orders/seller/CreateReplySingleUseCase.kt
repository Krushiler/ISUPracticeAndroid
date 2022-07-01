package com.bravedeveloper.domain.usecase.orders.seller

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.getorder.OrderInput
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyData
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class CreateReplySingleUseCase(private val ordersRepository: OrdersRepository): SingleUseCase<ResponseData<CreateReplyData>> {

    private var input : CreateReplyInput? = null

    fun saveInput(input : CreateReplyInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<CreateReplyData>>? {
        return input?.let { ordersRepository.createReply(it) }
    }

}