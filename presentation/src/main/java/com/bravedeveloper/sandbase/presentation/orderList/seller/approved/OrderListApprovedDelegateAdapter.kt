package com.bravedeveloper.sandbase.presentation.orderList.seller.approved

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerApprovedBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListApprovedDelegateAdapter(
    private val labelClick: OnOrderListItemClick<OrderItemApproved>,
    private val phoneClickListener: OnOrderListItemClick<OrderItemApproved>
) :
    ViewBindingDelegateAdapter<OrderItemApproved, ItemOrderSellerApprovedBinding>(
        ItemOrderSellerApprovedBinding::inflate
    ) {

    override fun isForViewType(item: Any) = item is OrderItemApproved

    override fun OrderItemApproved.getItemId() = title.toString()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun ItemOrderSellerApprovedBinding.onBind(item: OrderItemApproved) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentaryOrder
        commentaryResponse.text = item.commentaryResponse
        phoneButton.text = item.phone

        cost.text = item.cost
        myResponseButton.setOnClickListener {
            if (myResponseButton.isChecked) {
                myResponseLayout.visibility = View.VISIBLE
                myResponseButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                     null,
                    AppCompatResources.getDrawable(
                        myResponseLayout.context,
                        R.drawable.ic_arrow_drop_up
                    ),
                    null
                )
            } else {
                myResponseLayout.visibility = View.GONE
                myResponseButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(
                        myResponseLayout.context,
                        R.drawable.ic_arrow_drop_down
                    ),
                    null
                )
            }
        }
        phoneButton.setOnClickListener {
            phoneClickListener.onClick(item)
        }
        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }
    }

}