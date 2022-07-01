package com.bravedeveloper.sandbase.presentation.base.views.errorabletinputlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputLayout

class ErrorableInputLayout(context: Context, attributeSet: AttributeSet): TextInputLayout(context, attributeSet) {

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (editText is ErrorableTextInputEditText) {
            (editText as ErrorableTextInputEditText).setErrorState(enabled)
        }
    }

}