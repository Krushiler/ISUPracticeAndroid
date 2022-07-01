package com.bravedeveloper.sandbase.presentation.notifications.buyerOrder

import android.annotation.SuppressLint
import android.content.Context
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemNotifOrderBinding
import com.bravedeveloper.sandbase.presentation.notifications.NotificationItem
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class NewNotifAdapter(
    private val context: Context,
    private val labelClick: OnOrderListItemClick<NotificationItem>,
    private val approveClick: OnOrderListItemClick<NewNotifItem>
) :
    ViewBindingDelegateAdapter<NewNotifItem, ItemNotifOrderBinding>(ItemNotifOrderBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is NewNotifItem

    override fun NewNotifItem.getItemId(): Any = orderId

    @SuppressLint("SetTextI18n")
    override fun ItemNotifOrderBinding.onBind(item: NewNotifItem) {

        item.apply {
            orderTypeAndWeightTv.text = title
            orderBuyerNameTv.text = sellerName
            orderCommentaryTv.text = sellerComment
            orderFinalCost.text = context.getString(R.string.afforded_order_price, price)
            tvTimeAgo.text = timeAgo
        }

        orderTypeAndWeightTv.setOnClickListener {
            labelClick.onClick(item)
        }

        assignButton.setOnClickListener {
            approveClick.onClick(item)
        }

    }
}