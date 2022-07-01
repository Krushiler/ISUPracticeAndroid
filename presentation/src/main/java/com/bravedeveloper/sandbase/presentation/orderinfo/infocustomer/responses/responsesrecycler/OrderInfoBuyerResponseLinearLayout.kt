package com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.responsesrecycler

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bravedeveloper.sandbase.R

class OrderInfoBuyerResponseLinearLayout(context : Context, attrs : AttributeSet) : LinearLayout(context, attrs) {
    companion object {
        val STATE_AFFORDED = intArrayOf(R.attr.state_afforded)
    }

    private var isAfforded = false

    fun setAfforded(isAfforded : Boolean) {
        this.isAfforded = isAfforded
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isAfforded) {
            mergeDrawableStates(drawableState, STATE_AFFORDED)
        }
        return drawableState
    }

}