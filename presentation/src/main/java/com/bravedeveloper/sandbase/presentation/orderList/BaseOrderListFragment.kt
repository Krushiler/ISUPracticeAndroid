package com.bravedeveloper.sandbase.presentation.orderList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderRecyclerBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderList.base.OrderListViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.sellerfilter.SellerFilterViewModel
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter

abstract class BaseOrderListFragment :  BaseBindingFragment<FragmentOrderRecyclerBinding>(
    FragmentOrderRecyclerBinding::inflate) {

    protected val ordersViewModel: OrdersViewModel by activityViewModels()
    protected open val orderListViewModel: OrderListViewModel by viewModels()
    private val sellerFilterViewModel: SellerFilterViewModel by activityViewModels()
    protected val orderInfoViewModel: OrderInfoViewModel by activityViewModels()

    protected abstract val adapter: CompositeDelegateAdapter
    protected abstract val defaultFilterSettings: BaseOrderListSearchFilter

    private var ordersCount = 0

    override fun onResume() {
        super.onResume()
        ordersViewModel.setCountOrders(ordersCount)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellerFilterViewModel.getFilterLiveData().observe(viewLifecycleOwner) {
            val filter = it.copy()
            filter.sort = defaultFilterSettings.sort
            filter.role = defaultFilterSettings.role
            filter.searchType = defaultFilterSettings.searchType
            filter.needOwnUserId = defaultFilterSettings.needOwnUserId
            orderListViewModel.getOrders(filter)
        }

        orderListViewModel.getOrdersCountLiveData().observe(viewLifecycleOwner) {
            ordersCount = it
            if (ordersCount == 0) {
                binding.orderRecyclerView.visibility = View.GONE
                binding.noOrderLayout.visibility = View.VISIBLE
            } else {
                binding.orderRecyclerView.visibility = View.VISIBLE
                binding.noOrderLayout.visibility = View.GONE
            }
            if (isResumed) {
                ordersViewModel.setCountOrders(ordersCount)
            }
        }

        orderListViewModel.getOrdersLiveData().observe(viewLifecycleOwner) {
            addOrdersToAdapter(it)
        }

        binding.apply {
            orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerView.adapter = adapter
            orderRecyclerView.addItemDecoration(
                OrderItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.vertical_offer_item_margin),
                    resources.getDimensionPixelSize(R.dimen.horizontal_offer_item_margin)
                )
            )
            orderRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        orderListViewModel.loadMore()
                    }
                }
            })
        }
    }

    protected abstract fun addOrdersToAdapter(orders: List<Order>)

    protected fun goToOrder(number: Int) {
        orderInfoViewModel.getOrder(number)
        orderListViewModel.goToOrderInfoScreen()
    }

}