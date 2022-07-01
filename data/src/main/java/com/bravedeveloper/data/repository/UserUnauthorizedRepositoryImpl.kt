package com.bravedeveloper.data.repository

import com.bravedeveloper.data.remote.api.SandBaseService
import com.bravedeveloper.data.remote.api.request.Request
import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.order.unauthorized.OrdersUnauthorizedData
import com.bravedeveloper.domain.repository.UserUnauthorizedRepository
import io.reactivex.Single
import org.json.JSONObject

class UserUnauthorizedRepositoryImpl (
    private val api : SandBaseService
        ) : UserUnauthorizedRepository {
    override fun getOrdersUnauthorized(): Single<ResponseData<OrdersUnauthorizedData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getOrdersUnauthorized())
        return api.getOrdersUnauthorized(paramObject.toString())
    }
}