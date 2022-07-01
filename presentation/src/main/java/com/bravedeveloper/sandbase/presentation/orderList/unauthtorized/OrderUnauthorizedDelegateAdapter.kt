package com.bravedeveloper.sandbase.presentation.orderList.unauthtorized

import android.view.View
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerNewBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderUnauthorizedDelegateAdapter(private val responseClickListener: View.OnClickListener) :
    ViewBindingDelegateAdapter<OrderUnauthorizedItem, ItemOrderSellerNewBinding>(ItemOrderSellerNewBinding::inflate) {
    override fun isForViewType(item: Any) = item is OrderUnauthorizedItem

    override fun OrderUnauthorizedItem.getItemId() = id

    override fun ItemOrderSellerNewBinding.onBind(item: OrderUnauthorizedItem) {
        tvTitle.text = item.title
        tvGeolocation.text = item.destination
        tvCommentary.text = item.commentary
        tvDateAndTime.text = item.dateAndTime
        respondButton.setOnClickListener(responseClickListener)
    }

}