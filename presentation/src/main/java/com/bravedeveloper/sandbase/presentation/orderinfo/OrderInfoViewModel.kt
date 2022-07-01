package com.bravedeveloper.sandbase.presentation.orderinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyInput
import com.bravedeveloper.domain.model.city.order.getorder.OrderInput
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersInput
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyInput
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.domain.usecase.orders.GetOrderSingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.ApproveReplySingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.RemoveOrdersSingleUseCase
import com.bravedeveloper.domain.usecase.orders.seller.RemoveReplySingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderItem
import com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderresponses.MyReply
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderInfoViewModel @Inject constructor(
    private val router: Router,
    private val user: User,
    private val getOrderSingleUseCase: GetOrderSingleUseCase,
    private val removeReplySingleUseCase: RemoveReplySingleUseCase,
    private val removeOrdersSingleUseCase: RemoveOrdersSingleUseCase,
    private val approveReplyUseCase: ApproveReplySingleUseCase
) : BaseViewModel() {

    private val orderLiveData = MutableLiveData<OrderItem>()
    private val myReplyLiveData = MutableLiveData<MyReply>()

    private var lastNumber = 0

    fun getOrder(number: Int) {
        getOrderSingleUseCase.saveInput(OrderInput(number))
        getOrderSingleUseCase.execute()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({

                val userRoleEnum = if (it.data?.order?.order?.customer?.id == user.user?.userId) {
                    UserRoleEnum.CUSTOMER
                } else {
                    UserRoleEnum.SELLER
                }

                val orderStatusEnum = it.data?.order?.order?.status

                var hasMyResponse = false
                it.data?.order?.order?.replies?.forEach { reply ->
                    if (reply.seller?.id == user.user?.userId) hasMyResponse = true
                    myReplyLiveData.value = MyReply(reply.seller?.name, reply.price, reply.comment, reply.id.orEmpty(), it.data?.order?.order?.id.toString())
                }

                var hasResponses = false
                it.data?.order?.order?.replies?.let { replies ->
                    if (replies.isNotEmpty()) hasResponses = true
                }

                val orderResult = it.data?.order?.order

                val orderInfoResult =
                    orderStatusEnum?.let { status ->
                        OrderInfoType(
                            status,
                            userRoleEnum,
                            hasMyResponse,
                            hasResponses
                        )
                    }

                if (orderResult != null && orderInfoResult != null) {
                    orderLiveData.value = OrderItem(orderResult, orderInfoResult)
                }

                lastNumber = number

            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun removeReply(orderId: String, replyId: String) {
        removeReplySingleUseCase.saveInput(RemoveReplyInput(orderId, replyId))
        removeReplySingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                router.exit()
            },{
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun removeOrder() {
        if (orderLiveData.value?.order?.id != null) {
            removeOrdersSingleUseCase.saveInput(RemoveOrdersInput(listOf(orderLiveData.value?.order?.id!!)))
            removeOrdersSingleUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    router.exit()
                },{
                    Log.e(it::class.simpleName, it.message.toString())
                })?.untilCleared()
        }
    }

    fun reloadOrder() {
        getOrder(lastNumber)
    }

    fun approveReply(orderId: String, replyId: String) {
        approveReplyUseCase.saveInput(ApproveReplyInput(orderId, replyId))
        approveReplyUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                reloadOrder()
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun getOrderLiveData(): LiveData<OrderItem> = orderLiveData

    fun getMyReplyLiveData(): LiveData<MyReply> = myReplyLiveData
}