package com.bravedeveloper.sandbase.presentation.orderList.seller.response

import android.app.AlertDialog
import android.text.Layout
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingResponseBinding
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerResponseMapBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.seller.BaseOrderMapFragment
import com.bravedeveloper.sandbase.presentation.orderList.seller.OrderMapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMapResponsesFragment: BaseOrderMapFragment<ItemOrderSellerResponseMapBinding>(ItemOrderSellerResponseMapBinding::inflate) {

    override val orderMapViewModel: OrderMapResponseViewModel by viewModels()

    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.REPLIED_TO,
        true
    )

    private var replyId = ""

    override fun setOrderDataToBottomSheet(order: Order) {
        bottomSheetBinding.apply {
            tvTitle.text = order.getTitle(resources)
            tvTitle.setOnClickListener {
                order.number?.let { number -> goToOrder(number) }
            }
            tvGeolocation.text = order.destination.toString()
            tvCommentary.text = order.comment.toString()
            tvDateAndTime.text = if (order.time != null && order.date != null)
                getOrderFullTime(order.date.toString(), order.time!!, resources) else ""
            commentaryResponse.text = order.replies?.get(0)?.comment.toString()
            cost.text = order.replies?.get(0)?.price.toString() + " " + resources.getString(R.string.rubbles_short)
            replyId = order.replies?.get(0)?.id.toString()
            myResponseButton.setOnClickListener {
                if (myResponseButton.isChecked) {
                    myResponseLayout.visibility = View.VISIBLE
                    myResponseButton.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        AppCompatResources.getDrawable(
                            myResponseLayout.context,
                            R.drawable.ic_arrow_drop_up
                        ),
                        null
                    )
                } else {
                    myResponseLayout.visibility = View.GONE
                    myResponseButton.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(
                            myResponseLayout.context,
                            R.drawable.ic_arrow_drop_down
                        ),
                        null
                    )
                }
            }
            closeButton.setOnClickListener {
                closeBottomSheet()
            }
            deleteRespondButton.setOnClickListener {
                if (order.id != null && order.replies?.get(0)?.id != null) {
                    showRemoveReplyDialog(order.id.toString(), order.replies?.get(0)?.id.toString())
                }
            }
        }
    }

    private fun showRemoveReplyDialog(orderId: String, replyId: String) {
        binding.apply {
            val dialogDeleteResponseReply = AlertDialogDeletingResponseBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogDeleteResponseReply.root)
                .create()

            dialogDeleteResponseReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogDeleteResponseReply.confirmationDeletingResponseButton.setOnClickListener {
                orderMapViewModel.removeReply(
                    orderId, replyId
                )
                dialog.cancel()
            }
            dialog.show()
        }
    }

}