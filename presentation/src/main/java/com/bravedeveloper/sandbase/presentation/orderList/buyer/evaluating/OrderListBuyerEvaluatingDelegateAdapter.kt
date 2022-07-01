package com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating

import android.content.Context
import com.bravedeveloper.sandbase.databinding.ItemOrderCustomerEvaluatingBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bravedeveloper.sandbase.presentation.orderList.buyer.completed.OrderItemBuyerCompleted
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListBuyerEvaluatingDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemBuyerEvaluating>,
    private val respondsButtonClick: OnOrderListItemClick<OrderItemBuyerEvaluating>,
    private val context: Context?
) :
    ViewBindingDelegateAdapter<OrderItemBuyerEvaluating, ItemOrderCustomerEvaluatingBinding>(
        ItemOrderCustomerEvaluatingBinding::inflate
    ) {
    override fun isForViewType(item: Any): Boolean = item is OrderItemBuyerEvaluating

    override fun OrderItemBuyerEvaluating.getItemId(): Any = title

    override fun ItemOrderCustomerEvaluatingBinding.onBind(item: OrderItemBuyerEvaluating) {

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