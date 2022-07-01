package com.bravedeveloper.sandbase.presentation.orderList.seller.completed

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListCompletedFragment :
    BaseOrderListFragment() {

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.COMPLETED_ORDERS,
        true
    )

    override val adapter =
        CompositeDelegateAdapter(OrderListCompletedDelegateAdapter({
            it.number?.let { number -> goToOrder(number) }
        }, {}))

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemCompleted>()
        orders.map { order ->
            newOrders.add(
                OrderItemCompleted(
                    id = order.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = if (order.time != null && order.date != null)
                        getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                    commentaryOrder = order.comment.toString(),
                    phone = order.phone.toString(),
                    number = order.number
                )
            )
        }
        adapter.swapData(newOrders)
    }
}