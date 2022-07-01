package com.bravedeveloper.sandbase.presentation.orderList.seller.response

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingResponseBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListResponseFragment :
    BaseOrderListFragment() {

    override val orderListViewModel: OrderListResponseViewModel by viewModels()

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.REPLIED_TO,
        true
    )

    override lateinit var adapter: CompositeDelegateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = CompositeDelegateAdapter(OrderListResponseDelegateAdapter(
            context = requireContext(),
            labelClick = {
                it.number?.let { number -> goToOrder(number) }
            },
            deleteRespondButtonClick = {
                if (it.id != null && it.replyId != null) {
                    showRemoveReplyDialog(it.id, it.replyId)
                }
            })
        )

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemResponse>()
        orders.map { order ->
            newOrders.add(
                OrderItemResponse(
                    id = order.id.toString(),
                    replyId = order.replies?.get(0)?.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = if (order.time != null && order.date != null)
                        getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                    cost = order.replies?.get(0)?.price.toString() + " " + resources.getString(R.string.rubbles_short),
                    commentaryOrder = order.comment.toString(),
                    commentaryResponse = order.replies?.get(0)?.comment.toString(),
                    number = order.number,
                    customer = order.customer,
                    seller = order.seller
                )
            )
        }
        adapter.swapData(newOrders)
    }

    private fun showRemoveReplyDialog(orderId: String, replyId: String) {
        binding.apply {
            val dialogDeleteResponseReply =
                AlertDialogDeletingResponseBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogDeleteResponseReply.root)
                .create()

            dialogDeleteResponseReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogDeleteResponseReply.confirmationDeletingResponseButton.setOnClickListener {
                orderListViewModel.removeReply(
                    orderId, replyId
                )
                dialog.cancel()
            }
            dialog.show()
        }
    }

}



