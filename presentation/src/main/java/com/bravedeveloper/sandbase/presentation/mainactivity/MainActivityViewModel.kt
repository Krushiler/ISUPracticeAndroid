package com.bravedeveloper.sandbase.presentation.mainactivity

import com.bravedeveloper.domain.usecase.local.GetFirstTimeUseUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val router: Router,
    private val getFirstTimeUseUseCase: GetFirstTimeUseUseCase
) : BaseViewModel() {

    fun goToOnUnauthorizedLaunchAppScreen() {
        if (getFirstTimeUseUseCase.execute()) {
            navigateToSlidingBannersScreen()
        } else {
            navigateToUnauthorizedOrdersScreen()
        }
    }

    fun navigateToSlidingBannersScreen() {
        router.newRootChain(Screens.slidingBannersScreen())
    }

    fun navigateToOrderListScreen() {
        router.newRootChain(Screens.orderListScreen())
    }

    fun newRootFromOrderInfoScreen() {
        router.newRootChain(Screens.orderListScreen(), Screens.orderInfoScreen())
    }

    fun navigateToUnauthorizedOrdersScreen() {
        router.newRootChain(Screens.ordersUnauthorizedScreen())
    }

    fun goToAuthorizationScreen() {
        router.newRootChain(Screens.authorizationScreen())
    }

    fun goToSettingsScreen() {
        router.navigateTo(Screens.settingsScreen())
    }

    fun goToOrdersScreen() {
        router.navigateTo(Screens.orderListScreen())
    }

    fun goBack() {
        router.exit()
    }

}