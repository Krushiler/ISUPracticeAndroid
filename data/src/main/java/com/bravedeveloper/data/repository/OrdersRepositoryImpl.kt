package com.bravedeveloper.data.repository

import android.util.Log
import com.bravedeveloper.data.remote.api.SandBaseApi
import com.bravedeveloper.data.remote.api.SandBaseService
import com.bravedeveloper.data.remote.api.request.Request
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
import com.bravedeveloper.domain.repository.OrdersRepository
import io.reactivex.Single
import org.json.JSONObject

class OrdersRepositoryImpl(private val api: SandBaseService) : OrdersRepository {

    override fun getOrders(input: OrdersInput): Single<ResponseData<OrdersData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getOrders(input))
        return api.getOrders(paramObject.toString())
    }

    override fun getOrder(input: OrderInput): Single<ResponseData<OrderData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getOrder(input))
        return api.getOrder(paramObject.toString())
    }

    override fun createReply(input: CreateReplyInput): Single<ResponseData<CreateReplyData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.createReply(input))
        return api.createReply(paramObject.toString())
    }

    override fun getCargo(): Single<ResponseData<CargoData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getCargo())
        return api.getCargo(paramObject.toString())
    }

    override fun orderCreate(input: OrderCreateInput): Single<ResponseData<OrderCreateData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.orderCreate(input))
        return api.orderCreate(paramObject.toString())
    }

    override fun removeReply(input: RemoveReplyInput): Single<ResponseData<RemoveReplyData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.removeReply(input))
        return api.removeReply(paramObject.toString())
    }

    override fun removeOrders(input: RemoveOrdersInput): Single<ResponseData<RemoveOrdersData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.removeOrders(input))
        return api.removeOrders(paramObject.toString())
    }

    override fun approveReply(input: ApproveReplyInput): Single<ResponseData<ApproveReplyData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.approveReply(input))
        return api.approveReply(paramObject.toString())
    }

}