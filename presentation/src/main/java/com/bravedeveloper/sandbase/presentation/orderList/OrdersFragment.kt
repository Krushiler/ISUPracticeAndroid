package com.bravedeveloper.sandbase.presentation.orderList

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.DialogOrdersPresentationStyleBinding
import com.bravedeveloper.sandbase.databinding.DialogResetPasswordBinding
import com.bravedeveloper.sandbase.databinding.FragmentOrderListBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.presentation.mainactivity.MainActivity
import com.bravedeveloper.sandbase.presentation.notifications.NotificationsViewModel
import com.bravedeveloper.sandbase.presentation.orderList.buyer.active.OrderListActiveFragment
import com.bravedeveloper.sandbase.presentation.orderList.buyer.cancelled.OrderListCancelledFragment
import com.bravedeveloper.sandbase.presentation.orderList.buyer.completed.OrderListBuyerCompletedFragment
import com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating.OrderListBuyerEvaluatingFragment
import com.bravedeveloper.sandbase.presentation.orderList.buyer.onexecution.OrderListBuyerOnExecutionFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.OrderPresentationEnum
import com.bravedeveloper.sandbase.presentation.orderList.seller.approved.OrderListApprovedFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.approved.OrderMapApprovedFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.completed.OrderListCompletedFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.completed.OrderMapCompletedFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.evaluating.OrderListEvaluatingFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.evaluating.OrderMapEvaluatingFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.neworders.OrderListNewFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.neworders.OrderMapNewFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.response.OrderListResponseFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.response.OrderMapResponsesFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment :
    BaseBindingFragment<FragmentOrderListBinding>(FragmentOrderListBinding::inflate) {

    private val viewModel: OrdersViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val viewPagerViews: MutableList<OrdersFragmentItem> = mutableListOf()
    private var ordersPresentationEnum = OrderPresentationEnum.LIST
    private val notificationsViewModel: NotificationsViewModel by viewModels()

    private var userRoleEnum: UserRoleEnum? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderPager.isUserInputEnabled = false

        notificationsViewModel.getReplies()

        binding.roleSwitcher.setOnCheckedChangeListener(
            {
                viewModel.setRole(UserRoleEnum.CUSTOMER)
        }, {
                viewModel.setRole(UserRoleEnum.SELLER)
        })

        userViewModel.getUser().observe(viewLifecycleOwner, { it ->
            binding.roleSwitcher.visibility = View.GONE
            userRoleEnum = it?.role
            createViewPager()
        })

        viewModel.getOrderPresentationLiveData().observe(viewLifecycleOwner) {

            ordersPresentationEnum = it
            createViewPager()
        }

        binding.includedToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_burger)
            setNavigationOnClickListener {
                (activity as MainActivity).openDrawer()
            }
            inflateMenu(R.menu.toolbar_menu_notifications)

            val notificationsMenuItem = menu.findItem(R.id.menu_notifications_button)

            notificationsViewModel.getHasUnreadRepliesLiveData().observe(viewLifecycleOwner) {
                if (it) {
                    notificationsMenuItem.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_ring_mini_unread)
                } else {
                    notificationsMenuItem.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_ring_mini)
                }
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_notifications_button -> {
                        viewModel.goToNotificationsScreen()
                        true
                    }
                    else -> false
                }
            }
            binding.orderPager.offscreenPageLimit = 3
        }

        viewModel.getCountOrders().observe(viewLifecycleOwner) { count ->
            binding.ordersLabelCountView.text = "$count"
        }

        binding.apply {
            btnKebab.setOnClickListener {
                showOrdersPresentationDialog()
            }

            btnFilterSettings.setOnClickListener {
                viewModel.goToFilterSettingsScreen()
            }

            orderButton.setOnClickListener {
                viewModel.goToOrderCreationScreen()
            }
        }

    }

    private fun createViewPager() {
        if (userRoleEnum != null) {
            when (userRoleEnum) {
                UserRoleEnum.CUSTOMER -> {
                    showLayoutForRole(userRoleEnum)
                    createTabLayoutFromFragmentList(getBuyerFragments())
                }
                UserRoleEnum.SELLER -> {
                    showLayoutForRole(userRoleEnum)
                    if (ordersPresentationEnum == OrderPresentationEnum.LIST) {
                        createTabLayoutFromFragmentList(getSellerFragments())
                    } else if (ordersPresentationEnum == OrderPresentationEnum.MAP){
                        createTabLayoutFromFragmentList(getSellerMapFragments())
                    }
                }
                UserRoleEnum.DISPATCHER -> {
                    binding.roleSwitcher.visibility = View.VISIBLE
                    viewModel.getRole().observe(viewLifecycleOwner) {
                        when (it) {
                            UserRoleEnum.CUSTOMER -> {
                                showLayoutForRole(it)
                                createTabLayoutFromFragmentList(getBuyerFragments())
                            }
                            UserRoleEnum.SELLER -> {
                                showLayoutForRole(it)
                                if (ordersPresentationEnum == OrderPresentationEnum.LIST) {
                                    createTabLayoutFromFragmentList(getSellerFragments())
                                } else if (ordersPresentationEnum == OrderPresentationEnum.MAP){
                                    createTabLayoutFromFragmentList(getSellerMapFragments())
                                }
                            }
                        }
                    }
                }
                else -> {
                    showLayoutForRole(UserRoleEnum.CUSTOMER)
                    createTabLayoutFromFragmentList(getBuyerFragments())
                }
            }
        }
    }

    private fun showOrdersPresentationDialog() {
        binding.apply {
            val dialogOrdersPresentation =
                DialogOrdersPresentationStyleBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogOrdersPresentation.root)
                .create()

            dialogOrdersPresentation.buttonCancel.setOnClickListener {
                dialog.dismiss()
            }

            viewModel.getOrderPresentationLiveData().observe(viewLifecycleOwner) {
                ordersPresentationEnum = it
            }

            if (ordersPresentationEnum == OrderPresentationEnum.LIST) {
                dialogOrdersPresentation.radioButtonList.isChecked = true
            } else if (ordersPresentationEnum == OrderPresentationEnum.MAP) {
                dialogOrdersPresentation.radioButtonMap.isChecked = true
            }

            dialogOrdersPresentation.buttonReady.setOnClickListener {
                if (dialogOrdersPresentation.radioButtonList.isChecked) {
                    ordersPresentationEnum = OrderPresentationEnum.LIST
                } else if (dialogOrdersPresentation.radioButtonMap.isChecked) {
                    ordersPresentationEnum = OrderPresentationEnum.MAP
                }
                viewModel.setOrdersPresentationStyle(ordersPresentationEnum)
                dialog.cancel()
            }

            dialog.show()
        }
    }

    private fun showLayoutForRole(typeUser: UserRoleEnum?) {
        binding.apply {
            when (typeUser) {
                UserRoleEnum.SELLER -> {
                    orderButtonLayout.visibility = View.GONE
                    sellerFilterItems.visibility = View.VISIBLE
                }
                UserRoleEnum.CUSTOMER -> {
                    orderButtonLayout.visibility = View.VISIBLE
                    sellerFilterItems.visibility = View.GONE
                }
            }
        }
    }

    private fun createMarginBetweenTabs() {
        for (i in 0 until binding.ordersTabLayout.tabCount) {
            val tabItem = (binding.ordersTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val marginParams = tabItem.layoutParams as ViewGroup.MarginLayoutParams
            marginParams.marginStart = resources.getDimension(R.dimen.tab_item_spacing).toInt()
            if (i == binding.ordersTabLayout.tabCount - 1) marginParams.marginEnd =
                marginParams.marginStart
            tabItem.requestLayout()
        }
    }

    private fun getBuyerFragments(): MutableList<OrdersFragmentItem> {
        viewPagerViews.clear()
        val orderFragments = mutableListOf(
            OrdersFragmentItem(
                OrderListActiveFragment(),
                resources.getString(R.string.active_orders)
            ),
            OrdersFragmentItem(
                OrderListBuyerOnExecutionFragment(),
                resources.getString(R.string.exec_orders)
            ),
            OrdersFragmentItem(
                OrderListBuyerEvaluatingFragment(),
                resources.getString(R.string.evaluating_orders)
            ),
            OrdersFragmentItem(
                OrderListBuyerCompletedFragment(),
                resources.getString(R.string.completed_orders)
            ),
            OrdersFragmentItem(
                OrderListCancelledFragment(),
                resources.getString(R.string.cancelled_orders)
            ),
        )
        viewPagerViews.addAll(orderFragments)
        return orderFragments
    }

    private fun getSellerFragments(): MutableList<OrdersFragmentItem> {
        viewPagerViews.clear()
        val orderFragments = mutableListOf(
            OrdersFragmentItem(
                OrderListNewFragment(),
                resources.getString(R.string.new_orders)
            ),
            OrdersFragmentItem(
                OrderListResponseFragment(),
                resources.getString(R.string.my_responses)
            ),
            OrdersFragmentItem(
                OrderListApprovedFragment(),
                resources.getString(R.string.approved)
            ),
            OrdersFragmentItem(
                OrderListEvaluatingFragment(),
                resources.getString(R.string.evaluating_orders)
            ),
            OrdersFragmentItem(
                OrderListCompletedFragment(),
                resources.getString(R.string.completed_orders)
            )
        )
        viewPagerViews.addAll(orderFragments)
        return orderFragments
    }

    private fun getSellerMapFragments(): MutableList<OrdersFragmentItem> {
        viewPagerViews.clear()
        val orderFragments = mutableListOf(
            OrdersFragmentItem(
                OrderMapNewFragment(),
                resources.getString(R.string.new_orders)
            ),
            OrdersFragmentItem(
                OrderMapResponsesFragment(),
                resources.getString(R.string.my_responses)
            ),
            OrdersFragmentItem(
                OrderMapApprovedFragment(),
                resources.getString(R.string.approved)
            ),
            OrdersFragmentItem(
                OrderMapEvaluatingFragment(),
                resources.getString(R.string.evaluating_orders)
            ),
            OrdersFragmentItem(
                OrderMapCompletedFragment(),
                resources.getString(R.string.completed_orders)
            )
        )
        viewPagerViews.addAll(orderFragments)
        return orderFragments
    }

    private fun createTabLayoutFromFragmentList(fragmentList: MutableList<OrdersFragmentItem>) {
        val viewPageAdapter = OrdersTabAdapter(this, fragmentList)
        binding.orderPager.adapter = viewPageAdapter
        TabLayoutMediator(binding.ordersTabLayout, binding.orderPager) { tab, position ->
            tab.text = fragmentList[position].title
        }.attach()
        createMarginBetweenTabs()
    }
}


