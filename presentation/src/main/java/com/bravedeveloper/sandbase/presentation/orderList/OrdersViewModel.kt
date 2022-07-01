package com.bravedeveloper.sandbase.presentation.orderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.domain.usecase.orders.OrdersUnauthorizedSingleUseCase
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.bravedeveloper.sandbase.presentation.orderList.seller.OrderPresentationEnum
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    private val orderPresentationLiveData = MutableLiveData(OrderPresentationEnum.LIST)
    private val countOrders = MutableLiveData(0)
    private val compositeDisposable = CompositeDisposable()
    private val pickedRoleLiveData = MutableLiveData(UserRoleEnum.CUSTOMER)

    fun setOrdersPresentationStyle(orderPresentationEnum: OrderPresentationEnum) {
        orderPresentationLiveData.value = orderPresentationEnum
    }

    fun setRole(role: UserRoleEnum) {
        pickedRoleLiveData.value = role
    }

    fun getRole(): LiveData<UserRoleEnum> = pickedRoleLiveData

    fun getCountOrders(): LiveData<Int> = countOrders

    fun getOrderPresentationLiveData(): LiveData<OrderPresentationEnum> = orderPresentationLiveData

    fun setCountOrders(count: Int) {
        countOrders.value = count
    }

    fun goToAuthorizationScreen() {
        router.navigateTo(Screens.authorizationScreen())
    }

    fun goToFilterSettingsScreen() {
        router.navigateTo(Screens.sellerFilterScreen())
    }

    fun goToOrderCreationScreen() {
        router.navigateTo(Screens.orderCheckoutScreen())
    }

    fun goToNotificationsScreen() {
        router.navigateTo(Screens.notificationsScreen())
    }

    fun goToSettingsScreen() {
        router.navigateTo(Screens.settingsScreen())
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}