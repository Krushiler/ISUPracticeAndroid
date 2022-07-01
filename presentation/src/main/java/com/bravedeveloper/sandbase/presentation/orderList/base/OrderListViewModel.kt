package com.bravedeveloper.sandbase.presentation.orderList.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersFilter
import com.bravedeveloper.domain.model.city.order.search.OrdersInput
import com.bravedeveloper.domain.usecase.orders.GetOrdersSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.bravedeveloper.sandbase.presentation.sellerfilter.UserFilter
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
open class OrderListViewModel @Inject constructor(
    private val getOrdersSingleUseCase: GetOrdersSingleUseCase,
    private val user: User,
    private val router: Router
) : BaseViewModel() {

    private val takeOrdersCount: Int = 5
    private var skipOrdersCount: Int = 0

    private val ordersCountLiveData = MutableLiveData<Int>()
    private val ordersLiveData = MutableLiveData<List<Order>>()

    private var lastFilter = UserFilter(null, null, null, null, null, null, null)
    private var hasMoreOrders = false

    fun getOrders(userFilter: UserFilter) {
        lastFilter = userFilter
        updateFilter(lastFilter)
        ordersLiveData.value = listOf()
        loadOrdersWithSavedFilter()
    }

    fun loadMore() {
        if (hasMoreOrders) {
            skipOrdersCount += takeOrdersCount
            updateFilter(lastFilter)
            loadOrdersWithSavedFilter()
        }
    }

    fun reloadOrders() {
        skipOrdersCount = 0
        updateFilter(lastFilter)
        ordersLiveData.value = listOf()
        loadOrdersWithSavedFilter()
    }

    fun getOrdersLiveData(): LiveData<List<Order>> = ordersLiveData

    fun getOrdersCountLiveData(): LiveData<Int> = ordersCountLiveData

    private fun updateFilter(userFilter: UserFilter) {
        getOrdersSingleUseCase.saveInput(
            OrdersInput(
                skip = skipOrdersCount,
                take = takeOrdersCount,
                sort = userFilter.sort,
                filter = OrdersFilter(
                    searchType = userFilter.searchType,
                    role = userFilter.role,
                    userId = if (userFilter.needOwnUserId == true) user.user?.userId else null,
                    cityIds = null,
                    minVolume = userFilter.minimalValue,
                    maxVolume = userFilter.maximalValue,
                    materials = userFilter.materials
                )
            )
        )
    }

    private fun loadOrdersWithSavedFilter() {
        getOrdersSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val newOrders: MutableList<Order> =
                    if (ordersLiveData.value != null) ordersLiveData.value!!.toMutableList() else mutableListOf()
                hasMoreOrders = it.data?.orders?.pageInfo?.hasNextPage == true
                it.data?.orders?.items?.let { it1 -> newOrders.addAll(it1) }
                ordersLiveData.value = newOrders
                ordersCountLiveData.value = it.data?.orders?.pageInfo?.totalItems
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun goToOrderInfoScreen() {
        router.navigateTo(Screens.orderInfoScreen())
    }

}