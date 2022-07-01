package com.bravedeveloper.sandbase.presentation.orderList.buyer.active

import android.content.Context
import com.bravedeveloper.sandbase.databinding.ItemActiveOrderBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListActiveDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemActive>,
    private val respondButtonClick: OnOrderListItemClick<OrderItemActive>,
    private val context: Context?
) :
    ViewBindingDelegateAdapter<OrderItemActive, ItemActiveOrderBinding>(ItemActiveOrderBinding::inflate) {

    override fun isForViewType(item: Any) = item is OrderItemActive

    override fun OrderItemActive.getItemId(): Any = title

    override fun ItemActiveOrderBinding.onBind(item: OrderItemActive) {
        tvTitle.text = item.title
        activeOrderGeoTv.text = item.location
        if (item.commentary != null) activeOrderCommentaryTv.text = item.commentary
        activeOrderDateTv.text = item.dateAndTime
        textCountComments.text = item.countCommentary
        textCountViews.text = item.viewCount
        respondsButton.setOnClickListener {
            respondButtonClick.onClick(item)
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