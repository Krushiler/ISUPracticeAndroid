package com.bravedeveloper.sandbase.presentation.navigation

import com.bravedeveloper.sandbase.presentation.authorization.AuthorizationFragment
import com.bravedeveloper.sandbase.presentation.authorization.forgotpassword.ForgotPasswordFragment
import com.bravedeveloper.sandbase.presentation.notifications.NotificationsFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.map.OrderDetailsMapFragment
import com.bravedeveloper.sandbase.presentation.orderList.OrdersFragment
import com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating.OrderListBuyerEvaluatingFragment
import com.bravedeveloper.sandbase.presentation.orderList.unauthtorized.OrdersUnauthorizedFragment
import com.bravedeveloper.sandbase.presentation.ordercheckout.OrderCheckoutMapFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoFragment
import com.bravedeveloper.sandbase.presentation.sellerfilter.SellerFilterFragment
import com.bravedeveloper.sandbase.presentation.settings.SettingsFragment
import com.bravedeveloper.sandbase.presentation.slidingbanners.SlidingBannersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun slidingBannersScreen() = FragmentScreen { SlidingBannersFragment() }

    fun authorizationScreen() = FragmentScreen { AuthorizationFragment() }

    fun orderListScreen() = FragmentScreen { OrdersFragment() }

    fun orderInfoScreen() = FragmentScreen { OrderInfoFragment() }
    
    fun sellerFilterScreen() = FragmentScreen { SellerFilterFragment() }

    fun settingsScreen() = FragmentScreen { SettingsFragment() }

    fun mapScreen() = FragmentScreen{ OrderDetailsMapFragment() }

    fun notificationsScreen() = FragmentScreen { NotificationsFragment() }

    fun orderCheckoutScreen() = FragmentScreen { OrderCheckoutMapFragment() }

    fun forgotPasswordScreen() = FragmentScreen { ForgotPasswordFragment() }

    fun ordersUnauthorizedScreen() = FragmentScreen { OrdersUnauthorizedFragment() }

}