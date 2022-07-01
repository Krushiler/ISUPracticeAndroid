package com.bravedeveloper.sandbase.presentation.orderList.buyer.onexecution

import android.content.Context
import com.bravedeveloper.sandbase.databinding.ItemOnexecutionOrderBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating.OrderItemBuyerEvaluating
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListBuyerOnExecutionDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemBuyerOnExecution>,
    private val respondsButtonClick: OnOrderListItemClick<OrderItemBuyerOnExecution>,
    private val context: Context?
) :
    ViewBindingDelegateAdapter<OrderItemBuyerOnExecution, ItemOnexecutionOrderBinding>(
        ItemOnexecutionOrderBinding::inflate
    ) {
    override fun isForViewType(item: Any): Boolean = item is OrderItemBuyerOnExecution

    override fun OrderItemBuyerOnExecution.getItemId(): Any = title

    override fun ItemOnexecutionOrderBinding.onBind(item: OrderItemBuyerOnExecution) {
        tvTitle.text = item.title
        execOrderGeoTv.text = item.location
        if (item.commentary != null) execOrderCommentaryTv.text = item.commentary
        execOrderDateTv.text = item.dateAndTime
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