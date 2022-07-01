package com.bravedeveloper.sandbase.presentation.orderinfo.common

import android.util.Log
import android.view.View
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.OrderInfoItemBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderInfoItemListAdapter (private val addressClickListener: AddressClickListener? = null) :
    ViewBindingDelegateAdapter<OrderInfoItem<*>, OrderInfoItemBinding>(OrderInfoItemBinding::inflate) {

    override fun isForViewType(item: Any) = item is OrderInfoItem<*>

    override fun OrderInfoItem<*>.getItemId() = title

    override fun OrderInfoItemBinding.onBind(item: OrderInfoItem<*>) {
        infoTitle.setText(item.title)

        when (item.type) {
            OrderInfoItemType.DEFAULT -> infoDescription.setText(item.description.toString())
            OrderInfoItemType.ADDRESS -> {
                infoDescription.setText(item.description.toString())
                infoDescription.setOnClickListener { addressClickListener?.onAddressClick() }
            }
            OrderInfoItemType.RATING -> {
                if (item.description is Int) {
                    ratingBar.numStars = item.description
                    ratingBar.rating = item.description.toFloat()
                }
                infoDescription.visibility = View.INVISIBLE
                ratingBarLayout.visibility = View.VISIBLE
            }
            OrderInfoItemType.PHONE -> {
                infoDescription.setHint(R.string.your_response_has_not_yet_been_confirmed)
            }
            OrderInfoItemType.COMMENTARY -> {
                infoDescription.setText(item.description.toString())
                infoDescription.setHint(R.string.no_comment)
            }
            OrderInfoItemType.PRICE -> {
                infoDescription.setHint(R.string.you_have_not_yet_left_a_response)
            }
        }
    }

    interface AddressClickListener {
        fun onAddressClick()
    }
}