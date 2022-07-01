package com.bravedeveloper.sandbase.presentation.orderList.seller.approved

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListApprovedFragment :
    BaseOrderListFragment() {

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.APPROVED_REPLIES,
        true
    )

    override val adapter =
        CompositeDelegateAdapter(OrderListApprovedDelegateAdapter({
            it.number?.let { number -> goToOrder(number) }
        }, {}))

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemApproved>()
        orders.map { order ->
            newOrders.add(
                OrderItemApproved(
                    id = order.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = if (order.time != null && order.date != null)
                        getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                    cost = order.replies?.get(0)?.price.toString() + " " + resources.getString(R.string.rubbles_short),
                    commentaryOrder = order.comment.toString(),
                    commentaryResponse = order.replies?.get(0)?.comment.toString(),
                    phone = order.phone.toString(),
                    number = order.number
                )
            )
        }
        adapter.swapData(newOrders)
    }
}