package com.bravedeveloper.sandbase.presentation.settings.general

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SettingsTextInputItemDecoration(
    private val spaceSize: Int,
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
        }
    }
}