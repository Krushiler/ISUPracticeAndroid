package com.bravedeveloper.sandbase.presentation.orderList.buyer.completed

import android.content.Context
import com.bravedeveloper.sandbase.databinding.ItemOrderCustomerCompletedBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListBuyerCompletedDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemBuyerCompleted>,
    private val respondsButtonClick: OnOrderListItemClick<OrderItemBuyerCompleted>,
    private val context: Context?
) :
    ViewBindingDelegateAdapter<OrderItemBuyerCompleted, ItemOrderCustomerCompletedBinding>(
        ItemOrderCustomerCompletedBinding::inflate
    ) {
    override fun isForViewType(item: Any): Boolean = item is OrderItemBuyerCompleted

    override fun OrderItemBuyerCompleted.getItemId(): Any = title

    override fun ItemOrderCustomerCompletedBinding.onBind(item: OrderItemBuyerCompleted) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentary
        tvDateAndTime.text = item.dateAndTime
        textCountComments.text = item.countCommentary
        textCountViews.text = item.viewCount
        priceTextView.text = item.price
        respondsButton.setOnClickListener {
            respondsButtonClick.onClick(item)
        }
        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }
        if (item.avatar != null) {
            if (context != null)
                Glide.with(context)
                    .load(item.avatar)
                    .into(imageAvatar)
        }
    }
}