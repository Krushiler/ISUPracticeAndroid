package com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.responsesrecycler

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderInfoBuyerResponseBinding
import com.bravedeveloper.sandbase.presentation.notifications.buyerOrder.AdditionalInfoView
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide

import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderInfoBuyerResponseAdapter(
    private val context: Context,
    private val approveClick: OnOrderListItemClick<OrderInfoBuyerResponseItem>
) :
    ViewBindingDelegateAdapter<OrderInfoBuyerResponseItem, ItemOrderInfoBuyerResponseBinding>(
        ItemOrderInfoBuyerResponseBinding::inflate
    ) {
    override fun isForViewType(item: Any) = item is OrderInfoBuyerResponseItem

    override fun OrderInfoBuyerResponseItem.getItemId() = id

    override fun ItemOrderInfoBuyerResponseBinding.onBind(item: OrderInfoBuyerResponseItem) {
        orderBuyerNameTv.text = item.nameAndSurname
        if (item.rating != null) {
            orderRating.setRating(item.rating)
        } else {
            orderRating.setRating(0)
        }
        orderAdditionalInfo.updateInfo(item.isCheckedSeller, AdditionalInfoView.APPROVE_INFO)
        orderAdditionalInfo.updateInfo(item.isCashPayment, AdditionalInfoView.MONEY_INFO)
        orderAdditionalInfo.updateInfo(item.isCashlessPayment, AdditionalInfoView.SERTIFICATE_INFO)
        orderCommentaryTv.text = item.commentary
        orderFinalCost.text = context.getString(R.string.afforded_order_price, item.price)
        if (item.orderStatus == OrderStatusEnum.NEW) {
            assignButton.setOnClickListener {
                approveClick.onClick(item)
            }
        }
        if (item.isAffordedResponse) {
            responseTypeTitle.text = context.getString(R.string.afforded_order)
            newOrderGroup.visibility = View.GONE
            orderAdditionalInfo.visibility = View.VISIBLE
        } else {
            responseTypeTitle.text = context.getString(R.string.new_responce)
            newOrderGroup.visibility = View.VISIBLE
            orderAdditionalInfo.visibility = View.GONE
        }
    }
}