package com.bravedeveloper.sandbase.presentation.base.views.errorabletinputlayout

import android.content.Context
import android.util.AttributeSet
import com.bravedeveloper.sandbase.R
import com.google.android.material.textfield.TextInputEditText

class ErrorableTextInputEditText(context: Context, attributeSet: AttributeSet): TextInputEditText(context, attributeSet) {

    private val STATE_ERROR = intArrayOf(R.attr.state_error)
    private var isErrorState = false

    fun setErrorState(isError: Boolean) {
        isErrorState = isError
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace+1)
        if (isErrorState) mergeDrawableStates(drawableState, STATE_ERROR)
        return drawableState
    }

}