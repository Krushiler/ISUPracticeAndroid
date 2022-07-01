package com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.Me
import com.bravedeveloper.domain.model.user.MeData
import com.bravedeveloper.domain.model.user.rating.RateInput
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingAccountBinding
import com.bravedeveloper.sandbase.databinding.DialogFeedbackBuyerBinding
import com.bravedeveloper.sandbase.databinding.FragmentOrderRecyclerBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.OrderItemDecoration
import com.bravedeveloper.sandbase.presentation.orderList.OrdersViewModel
import com.bravedeveloper.sandbase.presentation.orderList.base.OrderListViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.sellerfilter.UserFilter
import com.bravedeveloper.sandbase.util.hideKeyboard
import com.bravedeveloper.sandbase.util.makeToast
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderListBuyerEvaluatingFragment
    : BaseOrderListFragment() {

    private val orderListEvaluatingViewModel: OrderListEvaluatingViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.CUSTOMER,
        OrdersSearchEnum.OWN_WAITING,
        true
    )

    override val adapter =
        CompositeDelegateAdapter(
            OrderListBuyerEvaluatingDelegateAdapter(
                labelClick = {
                    it.number?.let { number -> goToOrder(number) }
                },
                respondsButtonClick = { item ->
                    val dialogEvaluate =
                        DialogFeedbackBuyerBinding.inflate(layoutInflater)

                    val dialog = AlertDialog.Builder(requireContext())
                        .setView(dialogEvaluate.root)
                        .create()

                    dialogEvaluate.cancellationCrossImageButton.setOnClickListener {
                        hideKeyboard(activity = requireActivity())
                        dialog.dismiss()
                    }

                    dialogEvaluate.btnDialogEvaluateBuyerConfirm.setOnClickListener {
                        orderListEvaluatingViewModel.rateUser(
                            input = RateInput(
                                rate = dialogEvaluate.dialogEvaluateRatingBarBuyer.rating.toInt(),
                                completed = dialogEvaluate.dialogFeedbackRadioButtonOrderCompleted.isChecked,
                                comment = dialogEvaluate.commentEditText.text.toString(),
                                userId = userViewModel.getUserId(),
                                orderId = item.id
                            )
                        )
                    }

                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.show()
                }, context
            )
        )

    override fun addOrdersToAdapter(orders: List<Order>) {
        val newOrders = mutableListOf<OrderItemBuyerEvaluating>()
        orders.map { order ->
            newOrders.add(
                OrderItemBuyerEvaluating(
                    id = order.id.toString(),
                    title = order.getTitle(resources),
                    location = order.destination.toString(),
                    dateAndTime = if (order.time != null && order.date != null)
                        getOrderFullTime(order.date.toString(), order.time!!, resources) else "",
                    commentary = order.comment.toString(),
                    viewCount = order.views.toString(),
                    countCommentary = order.replies?.size.toString(),
                    avatar = order.customer?.avatar?.formats?.medium?.url,
                    price = order.price.toString() + " " + resources.getString(R.string.rubbles_short),
                    number = order.number
                )
            )
        }
        adapter.swapData(newOrders)
    }
}