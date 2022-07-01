package com.bravedeveloper.sandbase.presentation.orderList.seller.evaluating

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerEvaluatingBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bravedeveloper.sandbase.presentation.orderList.seller.approved.OrderItemApproved
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListEvaluatingDelegateAdapter (private val labelClick: OnOrderListItemClick<OrderItemEvaluating>,private val phoneClick: OnOrderListItemClick<OrderItemEvaluating>) :
    ViewBindingDelegateAdapter<OrderItemEvaluating, ItemOrderSellerEvaluatingBinding>(ItemOrderSellerEvaluatingBinding::inflate) {

    override fun isForViewType(item: Any) = item is OrderItemEvaluating

    override fun OrderItemEvaluating.getItemId() = id.toString()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun ItemOrderSellerEvaluatingBinding.onBind(item: OrderItemEvaluating) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentaryOrder
        tvDateAndTime.text = item.dateAndTime
        phoneButton.text = item.phone

        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }

        phoneButton.setOnClickListener {
            phoneClick.onClick(item)
        }
    }

}