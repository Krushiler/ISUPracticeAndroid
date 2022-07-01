package com.bravedeveloper.sandbase.presentation.orderList.unauthtorized

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderlistUnauthorizedBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.mainactivity.MainActivity
import com.bravedeveloper.sandbase.presentation.orderList.OrderItemDecoration
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersUnauthorizedFragment :
    BaseBindingFragment<FragmentOrderlistUnauthorizedBinding>(FragmentOrderlistUnauthorizedBinding::inflate) {

    private val viewModel: OrdersUnauthorizedViewModel by viewModels()
    private val ordersAdapter =
        CompositeDelegateAdapter(OrderUnauthorizedDelegateAdapter(View.OnClickListener {
            viewModel.goToAuthorizationScreen()
        }))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOrders()

        viewModel.getOrdersLiveData().observe(viewLifecycleOwner) {
            val orders = mutableListOf<OrderUnauthorizedItem>()
            it.map { order ->
                orders.add(
                    OrderUnauthorizedItem(
                        id = order.id.toString(),
                        title = order.getTitle(resources),
                        destination = order.destination.toString(),
                        dateAndTime = if (order.time != null && order.date != null)
                            getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                        commentary = order.comment.toString()
                    )
                )
            }
            ordersAdapter.swapData(orders)
        }

        binding.createOrderButton.setOnClickListener {
            viewModel.goToAuthorizationScreen()
        }

        binding.includedToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_burger)
            setNavigationOnClickListener {
                (activity as MainActivity).openDrawer()
            }
            inflateMenu(R.menu.toolbar_menu_notifications)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_notifications_button -> {
                        viewModel.goToAuthorizationScreen()
                        true
                    }
                    else -> false
                }
            }
        }

        binding.apply {
            orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerView.adapter = ordersAdapter
            orderRecyclerView.addItemDecoration(
                OrderItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.vertical_offer_item_margin),
                    resources.getDimensionPixelSize(R.dimen.horizontal_offer_item_margin)
                )
            )
        }

    }

}