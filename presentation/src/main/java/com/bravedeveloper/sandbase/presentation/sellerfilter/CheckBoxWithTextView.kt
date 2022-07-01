package com.bravedeveloper.sandbase.presentation.sellerfilter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.bravedeveloper.sandbase.R

import android.content.res.TypedArray
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class CheckBoxWithTextView(context: Context, attrs : AttributeSet) : LinearLayout(context, attrs) {

    private var checkBox : CheckBox
    private var textView : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.checker_with_text_layout, this, true)

        checkBox = findViewById(R.id.cwtl_CB)
        textView = findViewById(R.id.cwtl_TV)

        this.setOnClickListener { checkBox.performClick() }

        val attributesArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxWithTextView)

        textView.text = attributesArray.getText(R.styleable.CheckBoxWithTextView_android_text)
        checkBox.isChecked = attributesArray.getBoolean(R.styleable.CheckBoxWithTextView_android_checked, false)
        textView.setTextColor(attributesArray.getColor(R.styleable.CheckBoxWithTextView_android_textColor, ContextCompat.getColor(context ,R.color.black)))
        val fontFamilyId: Int =
            attributesArray.getResourceId(R.styleable.CheckBoxWithTextView_android_fontFamily, 0)
        if (fontFamilyId > 0) {
            textView.typeface = ResourcesCompat.getFont(getContext(), fontFamilyId)
        }

        attributesArray.recycle()
    }

    fun setChecked(isChecked : Boolean) {
        checkBox.isChecked = isChecked
    }

    fun isChecked() = checkBox.isChecked

}