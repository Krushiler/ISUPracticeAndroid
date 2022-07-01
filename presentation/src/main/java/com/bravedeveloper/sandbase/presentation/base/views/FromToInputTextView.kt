package com.bravedeveloper.sandbase.presentation.base.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R

class FromToInputTextView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var fromToTextView: TextView
    private var fromToLayout: FrameLayout
    private var inputTextView: TextView

    var text: String = ""
        set(value) {
            inputTextView.text = value
            field = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_from_to_text_view, this, true)

        fromToTextView = findViewById(R.id.tv_from_to)
        fromToLayout = findViewById(R.id.layout_from_to)
        inputTextView = findViewById(R.id.tv_input)

        val attributesArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.FromToInputTextView)

        val fromToTextNumber = attributesArray.getInt(R.styleable.FromToInputTextView_fromToText, 0)

        fromToTextView.setTextColor(
            attributesArray.getColor(
                R.styleable.FromToInputTextView_fromToColor,
                ContextCompat.getColor(context, R.color.black)
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fromToTextView.typeface = attributesArray.getFont(R.styleable.FromToInputTextView_fontFamily)
            inputTextView.typeface = attributesArray.getFont(R.styleable.FromToInputTextView_fontFamily)
        }

        fromToTextView.text = if (fromToTextNumber == 0) {
            context.getString(R.string.from)
        } else {
            context.getString(R.string.to)
        }

        fromToLayout.background =
            attributesArray.getDrawable(R.styleable.FromToInputTextView_android_background)

        inputTextView.setTextColor(
            attributesArray.getColor(
                R.styleable.FromToInputTextView_inputColor,
                ContextCompat.getColor(context, R.color.black)
            )
        )
        attributesArray.recycle()
    }
}