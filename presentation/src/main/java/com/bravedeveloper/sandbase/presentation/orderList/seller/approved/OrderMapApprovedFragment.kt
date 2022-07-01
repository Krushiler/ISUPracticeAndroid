package com.bravedeveloper.sandbase.presentation.orderList.seller.approved

import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerApprovedMapBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.seller.BaseOrderMapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMapApprovedFragment : BaseOrderMapFragment<ItemOrderSellerApprovedMapBinding>(ItemOrderSellerApprovedMapBinding::inflate) {
    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.APPROVED_REPLIES,
        true
    )

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
            phoneButton.text = order.phone.toString()
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
        }
    }
}