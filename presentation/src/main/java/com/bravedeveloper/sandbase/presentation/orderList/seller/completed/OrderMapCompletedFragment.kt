package com.bravedeveloper.sandbase.presentation.orderList.seller.completed

import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerCompletedMapBinding
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.seller.BaseOrderMapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMapCompletedFragment: BaseOrderMapFragment<ItemOrderSellerCompletedMapBinding>(ItemOrderSellerCompletedMapBinding::inflate) {
    override val defaultFilterSettings = BaseOrderListSearchFilter(
        OrdersSortEnum.DATE_DESC,
        UserRoleEnum.SELLER,
        OrdersSearchEnum.OWN_WAITING,
        true
    )

    override fun setOrderDataToBottomSheet(order: Order) {
        bottomSheetBinding.apply {
            tvTitle.text = order.getTitle(resources)
            tvGeolocation.text = order.destination.toString()
            tvCommentary.text = order.comment.toString()
            tvDateAndTime.text = if (order.time != null && order.date != null)
                getOrderFullTime(order.date.toString(), order.time!!, resources) else ""
            phoneButton.text = order.phone.toString()
            tvTitle.setOnClickListener {
                order.number?.let { number -> goToOrder(number) }
            }
            closeButton.setOnClickListener {
                closeBottomSheet()
            }
        }
    }
}