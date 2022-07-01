package com.bravedeveloper.sandbase.presentation.orderList

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OrderItemDecoration(
    private val spaceSize: Int,
    private val horizontalSpaceSize: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize
            }
            bottom = spaceSize
            left = horizontalSpaceSize
            right = horizontalSpaceSize
        }
    }
}