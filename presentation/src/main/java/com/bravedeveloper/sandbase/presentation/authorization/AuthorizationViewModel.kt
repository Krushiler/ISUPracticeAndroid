package com.bravedeveloper.sandbase.presentation.authorization

import androidx.lifecycle.ViewModel
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {
    fun goToOrdersScreen() {
        router.newRootChain(Screens.orderListScreen())
    }
    fun goToSettingsScreen() {
        router.navigateTo(Screens.settingsScreen())
    }
    fun goToForgotPasswordScreen() {
        router.navigateTo(Screens.forgotPasswordScreen())
    }
    fun goBack() {
        router.exit()
    }
}