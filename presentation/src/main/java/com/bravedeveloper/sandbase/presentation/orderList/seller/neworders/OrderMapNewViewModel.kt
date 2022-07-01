package com.bravedeveloper.sandbase.presentation.orderList.seller.neworders

import android.util.Log
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyInput
import com.bravedeveloper.domain.usecase.orders.GetOrdersSingleUseCase
import com.bravedeveloper.domain.usecase.orders.seller.CreateReplySingleUseCase
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.orderList.seller.OrderMapViewModel
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderMapNewViewModel @Inject constructor(
    getOrdersSingleUseCase: GetOrdersSingleUseCase,
    user: User,
    router: Router,
    private val createReplySingleUseCase: CreateReplySingleUseCase
): OrderMapViewModel(getOrdersSingleUseCase, user, router) {
    fun createReply(orderId: String, price: String, comment: String) {
        createReplySingleUseCase.saveInput(CreateReplyInput(orderId, price, comment))
        createReplySingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                reloadOrders()
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

}