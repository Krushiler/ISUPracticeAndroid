package com.bravedeveloper.domain.repository

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.cargo.CargoData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyInput
import com.bravedeveloper.domain.model.city.order.getorder.OrderData
import com.bravedeveloper.domain.model.city.order.getorder.OrderInput
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateData
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateInput
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersData
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersInput
import com.bravedeveloper.domain.model.city.order.search.OrdersData
import com.bravedeveloper.domain.model.city.order.search.OrdersInput
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyData
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyInput
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyData
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyInput
import io.reactivex.Single

interface OrdersRepository {

    fun getOrders(input: OrdersInput) : Single<ResponseData<OrdersData>>

    fun getOrder(input: OrderInput) : Single<ResponseData<OrderData>>

    fun createReply(input: CreateReplyInput) : Single<ResponseData<CreateReplyData>>

    fun getCargo() : Single<ResponseData<CargoData>>

    fun orderCreate(input: OrderCreateInput) : Single<ResponseData<OrderCreateData>>

    fun removeReply(input: RemoveReplyInput) : Single<ResponseData<RemoveReplyData>>

    fun removeOrders(input: RemoveOrdersInput) : Single<ResponseData<RemoveOrdersData>>

    fun approveReply(input: ApproveReplyInput) : Single<ResponseData<ApproveReplyData>>

}