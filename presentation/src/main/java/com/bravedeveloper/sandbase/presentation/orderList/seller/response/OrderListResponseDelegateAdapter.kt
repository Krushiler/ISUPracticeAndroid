package com.bravedeveloper.sandbase.presentation.orderList.seller.response

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.ItemOrderSellerResponseBinding
import com.bravedeveloper.sandbase.presentation.orderList.base.OnOrderListItemClick
import com.bumptech.glide.Glide
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class OrderListResponseDelegateAdapter(
    private val context: Context,
    private val labelClick: OnOrderListItemClick<OrderItemResponse>,
    private val deleteRespondButtonClick: OnOrderListItemClick<OrderItemResponse>
) :
    ViewBindingDelegateAdapter<OrderItemResponse, ItemOrderSellerResponseBinding>(
        ItemOrderSellerResponseBinding::inflate
    ) {

    override fun isForViewType(item: Any) = item is OrderItemResponse

    override fun OrderItemResponse.getItemId() = title.toString()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun ItemOrderSellerResponseBinding.onBind(item: OrderItemResponse) {
        tvTitle.text = item.title
        tvGeolocation.text = item.location
        tvCommentary.text = item.commentaryOrder
        tvDateAndTime.text = item.dateAndTime
        expandedResponse.text = item.commentaryResponse
        cost.text = context.getString(R.string.response_money_amount, item.cost)

        Glide.with(context)
            .load(item.customer?.avatar?.formats?.small?.url ?: getDrawable(context, R.drawable.photo_account))
            .into(ivAvatar)

        Glide.with(context)
            .load(item.seller?.avatar?.formats?.small?.url ?: getDrawable(context, R.drawable.photo_account))
            .into(ivSellerAvatar)

        expandMyResponse.setOnClickListener {
            expandMyResponse.isActivated = !expandMyResponse.isActivated
            myResponseExpandLayout.visibility =
                if (myResponseExpandLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        tvTitle.setOnClickListener {
            labelClick.onClick(item)
        }

        removeResponseButton.setOnClickListener {
            deleteRespondButtonClick.onClick(item)
        }
    }
}