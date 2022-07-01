package com.bravedeveloper.sandbase.presentation.orderList.unauthtorized

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.usecase.orders.OrdersUnauthorizedSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrdersUnauthorizedViewModel @Inject constructor(
    private val getOrdersUnauthorizedSingleUseCase: OrdersUnauthorizedSingleUseCase,
    private val router: Router
) : BaseViewModel() {

    private val ordersLiveData = MutableLiveData<List<Order>>()

    fun goToAuthorizationScreen() {
        router.navigateTo(Screens.authorizationScreen())
    }

    fun getOrders() {
        getOrdersUnauthorizedSingleUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ordersLiveData.postValue(it.data?.ordersUnauthorized?.items)
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            }).untilCleared()
    }

    fun getOrdersLiveData(): LiveData<List<Order>> = ordersLiveData

}