package com.bravedeveloper.sandbase.presentation.orderList.seller.neworders

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerNewBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListNewDelegateAdapter(
    private val context: Context?,
    private val labelClick: OnOrderListItemClick<OrderItemNew>,
    private val respondButtonClick: OnOrderListItemClick<OrderItemNew>
) :
    ViewBindingDelegateAdapter<OrderItemNew, ItemOrderSellerNewBinding>(ItemOrderSellerNewBinding::inflate) {

    override fun isForViewType(item: Any) = item is OrderItemNew

    override fun OrderItemNew.getItemId() = id.toString()

    override fun ItemOrderSellerNewBinding.onBind(item: OrderItemNew) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        if (item.commentary != null) {
            tvCommentary.text = item.commentary
        }
        tvDateAndTime.text = item.dateAndTime

        Log.d("AAA", "$context")

        if (context != null) {
            val url = item.customer?.avatar?.formats?.small?.url
            Glide.with(context)
                .load(url ?: R.drawable.photo_account)
                .into(ivAvatar)
        }

        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }
        respondButton.setOnClickListener {
            respondButtonClick.onClick(item)
        }
    }
}