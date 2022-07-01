package com.bravedeveloper.sandbase.presentation.orderList.seller.completed

import android.annotation.SuppressLint
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerCompletedBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListCompletedDelegateAdapter(private val labelClick: OnOrderListItemClick<OrderItemCompleted>, private val phoneClick: OnOrderListItemClick<OrderItemCompleted>) :
    ViewBindingDelegateAdapter<OrderItemCompleted, ItemOrderSellerCompletedBinding>(ItemOrderSellerCompletedBinding::inflate) {

    override fun isForViewType(item: Any) = item is OrderItemCompleted

    override fun OrderItemCompleted.getItemId() = id.toString()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun ItemOrderSellerCompletedBinding.onBind(item: OrderItemCompleted) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentaryOrder
        tvDateAndTime.text = item.dateAndTime
        phoneButton.text = item.phone

        phoneButton.setOnClickListener {
            phoneClick.onClick(item)
        }
        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }
    }

}