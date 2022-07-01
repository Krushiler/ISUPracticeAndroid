package com.bravedeveloper.domain.usecase.orders.customer

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyInput
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class ApproveReplySingleUseCase(private val repository: OrdersRepository) :
    SingleUseCase<ResponseData<ApproveReplyData>> {

    private var input: ApproveReplyInput? = null

    fun saveInput(input: ApproveReplyInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<ApproveReplyData>>? {
        return input?.let { repository.approveReply(it) }
    }

}