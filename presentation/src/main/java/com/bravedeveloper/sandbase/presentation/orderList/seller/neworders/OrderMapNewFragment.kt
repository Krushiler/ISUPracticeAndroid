package com.bravedeveloper.sandbase.presentation.orderList.seller.neworders

import android.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogSellerRespondBinding
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerNewMapBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.seller.BaseOrderMapFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.OrderMapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMapNewFragment: BaseOrderMapFragment<ItemOrderSellerNewMapBinding>(ItemOrderSellerNewMapBinding::inflate) {

    override val orderMapViewModel: OrderMapNewViewModel by viewModels()

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.NEW_ORDERS,
        true
    )

    override fun setOrderDataToBottomSheet(order: Order) {
        bottomSheetBinding.apply {
            tvTitle.setOnClickListener {
                order.number?.let { number -> goToOrder(number) }
            }
            tvTitle.text = order.getTitle(resources)
            tvCommentary.text = order.comment.toString()
            tvGeolocation.text = order.destination.toString()
            tvDateAndTime.text = if (order.time != null && order.date != null)
                getOrderFullTime(order.date.toString(), order.time!!, resources) else ""
            respondButton.setOnClickListener {
                order.id?.let { id -> createReply(id) }
            }
            closeButton.setOnClickListener {
                closeBottomSheet()
            }
        }
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
                orderMapViewModel.createReply(
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