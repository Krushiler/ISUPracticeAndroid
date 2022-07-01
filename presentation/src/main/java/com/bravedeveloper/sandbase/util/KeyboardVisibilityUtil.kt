package com.bravedeveloper.sandbase.util


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard(context: Context?) {
    val fsm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    fsm.hideSoftInputFromWindow(this.windowToken, 0)
}