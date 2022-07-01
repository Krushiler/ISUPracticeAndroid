package com.bravedeveloper.sandbase.presentation.orderList.seller.neworders

import android.app.ActionBar
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogSellerRespondBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListNewFragment :
    BaseOrderListFragment() {

    override lateinit var adapter: CompositeDelegateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = CompositeDelegateAdapter(OrderListNewDelegateAdapter(
            context = requireContext(),
            {
                it.number?.let { number -> goToOrder(number) }
            }, {
                it.id?.let { orderId -> createReply(orderId) }
            })
        )

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override val defaultFilterSettings: BaseOrderListSearchFilter = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.NEW_ORDERS,
        true
    )

    override val orderListViewModel: OrderListNewViewModel by viewModels()

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemNew>()
        orders.map { order ->
            newOrders.add(
                OrderItemNew(
                    id = order.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = if (order.time != null && order.date != null)
                        getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                    commentary = order.comment,
                    number = order.number,
                    customer = order.customer
                )
            )
        }
        adapter.swapData(newOrders)
    }

    private fun createReply(orderId: String) {
        showCreateReplyDialog(orderId)
    }

    private fun showCreateReplyDialog(orderId: String) {
        binding.apply {
            val dialogCreateReply = AlertDialogSellerRespondBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogCreateReply.root)
                .create()

            dialogCreateReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogCreateReply.confirmationDeletingResponseButton.setOnClickListener {
                orderListViewModel.createReply(
                    orderId,
                    dialogCreateReply.costEditText.text.toString(),
                    dialogCreateReply.commentEditText.text.toString()
                )
                dialog.cancel()
            }
            dialog.show()
        }
    }
}



