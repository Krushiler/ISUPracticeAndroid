package com.bravedeveloper.sandbase.presentation.settings.settingsnotifications

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R

class CustomAutoCompleteTextView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatAutoCompleteTextView(context, attributeSet) {

    private var ifShow = true

    fun enableDropDown() {
        ifShow = true
    }

    fun disableDropDown() {
        ifShow = false
    }

    override fun replaceText(text: CharSequence?) {
        super.replaceText(text)
    }

    override fun dismissDropDown() {
        disableDropDown()
        super.dismissDropDown()
    }

    override fun showDropDown() {
        if (!ifShow) return
        super.showDropDown()
    }
}