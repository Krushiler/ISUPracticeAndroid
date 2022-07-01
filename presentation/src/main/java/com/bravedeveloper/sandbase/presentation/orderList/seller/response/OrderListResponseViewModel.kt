package com.bravedeveloper.sandbase.presentation.orderList.seller.response

import android.util.Log
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyInput
import com.bravedeveloper.domain.usecase.orders.GetOrdersSingleUseCase
import com.bravedeveloper.domain.usecase.orders.seller.RemoveReplySingleUseCase
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.orderList.base.OrderListViewModel
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderListResponseViewModel @Inject constructor(
    getOrdersSingleUseCase: GetOrdersSingleUseCase,
    private val user: User,
    router: Router,
    private val removeReplySingleUseCase: RemoveReplySingleUseCase
) : OrderListViewModel(
    getOrdersSingleUseCase, user, router
) {
    fun removeReply(orderId: String, replyId: String) {
        removeReplySingleUseCase.saveInput(RemoveReplyInput(orderId, replyId))
        removeReplySingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                reloadOrders()
            },{
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }
}