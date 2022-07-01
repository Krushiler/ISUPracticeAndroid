package com.bravedeveloper.sandbase.presentation.slidingbanners

import androidx.lifecycle.ViewModel
import com.bravedeveloper.domain.usecase.local.SetFirstTimeUseUseCase
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlidingBannerViewModel @Inject constructor(
    private val router: Router,
    private val setFirstTimeUseUseCase: SetFirstTimeUseUseCase
) : ViewModel() {
    fun goToOrderListScreen() {
        setFirstTimeUseUseCase.execute(false)
        router.newRootChain(Screens.ordersUnauthorizedScreen())
    }
}