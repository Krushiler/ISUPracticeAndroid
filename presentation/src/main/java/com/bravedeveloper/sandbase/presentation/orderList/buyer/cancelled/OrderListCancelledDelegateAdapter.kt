package com.bravedeveloper.sandbase.presentation.orderList.buyer.cancelled

import android.content.Context
import com.bravedeveloper.sandbase.databinding.ItemOrderCustomerCancelledBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListCancelledDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemCancelled>,
    private val respondsButtonClick: OnOrderListItemClick<OrderItemCancelled>,
    private val context: Context?
) :
    ViewBindingDelegateAdapter<OrderItemCancelled, ItemOrderCustomerCancelledBinding>(
        ItemOrderCustomerCancelledBinding::inflate
    ) {

    override fun isForViewType(item: Any) = item is OrderItemCancelled

    override fun OrderItemCancelled.getItemId(): Any = title

    override fun ItemOrderCustomerCancelledBinding.onBind(item: OrderItemCancelled) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentary
        tvDateAndTime.text = item.dateAndTime
        textCountComments.text = item.countCommentary
        textCountViews.text = item.viewCount
        respondsButton.setOnClickListener {
            respondsButtonClick.onClick(item)
        }
        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }
        if (item.avatar != null) {
            if (context != null) {
                Glide.with(context)
                    .load(item.avatar)
                    .into(imageAvatar)
            }
        }
    }
}