package com.bravedeveloper.sandbase.presentation.mainactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.presentation.navigation.burger.ButtonWithImageView
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.util.notification.NotificationAction
import com.bravedeveloper.sandbase.util.notification.NotificationUtil
import com.bravedeveloper.sandbase.util.observeOnce
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val orderInfoViewModel: OrderInfoViewModel by viewModels()

    private lateinit var drawerLayout: DrawerLayout

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator = AppNavigator(this, R.id.main_fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SandBase)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        setupDrawer()
        userViewModel.getUser().observeOnce(this) {
            if (it != null) {
                showDrawerAuthorized()

                val intentExtras = intent.extras

                if (intentExtras != null) {
                    when (val action = NotificationUtil.getNotificationAction(intentExtras)) {
                        is NotificationAction.OrderDetails -> {
                            orderInfoViewModel.getOrder(action.orderNumber)
                            viewModel.newRootFromOrderInfoScreen()
                          }
                        else -> {
                            if (savedInstanceState == null) {
                                viewModel.navigateToOrderListScreen()
                            }
                        }
                    }
                } else {
                    if (savedInstanceState == null) {
                        viewModel.navigateToOrderListScreen()
                    }
                }
            } else {
                if (savedInstanceState == null) {
                    viewModel.goToOnUnauthorizedLaunchAppScreen()
                }
                showDrawerUnauthorized()
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    @SuppressLint("RtlHardcoded")
    fun openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    @SuppressLint("RtlHardcoded")
    private fun setupDrawer() {
        drawerLayout.findViewById<ButtonWithImageView>(R.id.btnSignIn)
            .setOnClickListener {
                viewModel.goToAuthorizationScreen()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }

        drawerLayout.findViewById<ButtonWithImageView>(R.id.btnOrders)
            .setOnClickListener {
                viewModel.goToOrdersScreen()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }

        drawerLayout.findViewById<ButtonWithImageView>(R.id.btnSettings)
            .setOnClickListener {
                viewModel.goToSettingsScreen()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }

        drawerLayout.findViewById<ButtonWithImageView>(R.id.btnSignOut)
            .setOnClickListener {
                userViewModel.signOut()
                viewModel.navigateToUnauthorizedOrdersScreen()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
    }

    private fun showDrawerAuthorized() {
        val bottomSigned = drawerLayout.findViewById<LinearLayout>(R.id.bottomContainerSigned)
        val bottomNotSigned =
            drawerLayout.findViewById<LinearLayout>(R.id.bottomContainerNotSigned)
        bottomSigned.visibility = View.VISIBLE
        bottomNotSigned.visibility = View.GONE
    }

    private fun showDrawerUnauthorized() {
        val bottomSigned = drawerLayout.findViewById<LinearLayout>(R.id.bottomContainerSigned)
        val bottomNotSigned =
            drawerLayout.findViewById<LinearLayout>(R.id.bottomContainerNotSigned)
        bottomSigned.visibility = View.GONE
        bottomNotSigned.visibility = View.VISIBLE
    }
}