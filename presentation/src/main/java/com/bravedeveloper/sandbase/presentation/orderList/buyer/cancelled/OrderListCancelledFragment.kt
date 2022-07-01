package com.bravedeveloper.sandbase.presentation.orderList.buyer.cancelled

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderRecyclerBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.OrderItemDecoration
import com.bravedeveloper.sandbase.presentation.orderList.OrdersViewModel
import com.bravedeveloper.sandbase.presentation.orderList.base.OrderListViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.sellerfilter.UserFilter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListCancelledFragment
    : BaseOrderListFragment() {

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.CUSTOMER,
        OrdersSearchEnum.CLOSED_ORDERS,
        true
    )

    override val adapter =
        CompositeDelegateAdapter(OrderListCancelledDelegateAdapter({
            it.number?.let { number -> goToOrder(number) }
        }, {}, context))

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemCancelled>()
        orders.map { order ->
            newOrders.add(
                OrderItemCancelled(
                    id = order.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = order.date.toString(),
                    commentary = order.comment.toString(),
                    viewCount = order.views.toString(),
                    countCommentary = order.replies?.size.toString(),
                    avatar = order.customer?.avatar?.formats?.medium?.url,
                    number = order.number
                )
            )
        }
        adapter.swapData(newOrders)
    }
}