package com.bravedeveloper.sandbase.presentation.notifications.affordedOrder

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemAffordedOrderBinding
import com.bravedeveloper.sandbase.presentation.notifications.NotificationItem
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bravedeveloper.sandbase.presentation.orderList.seller.neworders.OrderItemNew
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class AffordedOrderAdapter(
    private val context: Context,
    private val labelClick: OnOrderListItemClick<NotificationItem>
) :
    ViewBindingDelegateAdapter<AffordedOrderItem, ItemAffordedOrderBinding>(ItemAffordedOrderBinding::inflate) {
    override fun isForViewType(item: Any) = item is AffordedOrderItem

    override fun AffordedOrderItem.getItemId() = orderId

    @SuppressLint("SetTextI18n")
    override fun ItemAffordedOrderBinding.onBind(item: AffordedOrderItem) {

        item.apply {
            affordedOrderTimeAgo.text = timeAgo
            orderGeoPositionTv.text = geolocation
            orderTypeAndWeightTv.text = weight
            orderDateAndTimeTv.text = dateAndTime
            orderBuyerNameTv.text = buyerName
            orderCommentaryTv.text = buyerComment
            orderFinalCost.text = context.resources.getString(R.string.afforded_order_price, price)
        }

        if (item.buyerComment.isEmpty()) {
            orderCommentaryTv.apply {
                text = resources.getString(R.string.no_order_comment_buyer)
                setTextColor(ContextCompat.getColor(context, R.color.grey5))
            }
        }
        orderTypeAndWeightTv.setOnClickListener {
            labelClick.onClick(item)
        }
    }
}