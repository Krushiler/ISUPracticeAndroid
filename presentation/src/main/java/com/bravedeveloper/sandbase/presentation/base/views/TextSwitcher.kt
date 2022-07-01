package com.bravedeveloper.sandbase.presentation.base.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bravedeveloper.sandbase.R

class TextSwitcher(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var checkedLeftEvent = {}
    private var checkedRightEvent = {}
    private var checkedListener: OnCheckedTextChangeListener =
        object : OnCheckedTextChangeListener {
            override fun onCheckedChange(isRightChecked: Boolean) {}
        }

    private var radioLeft: RadioButton
    private var radioRight: RadioButton
    private var radioGroup: RadioGroup
    private var rightDrawable: Drawable?
    private var leftDrawable: Drawable?
    private var rightChecked = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_text_switcher, this, true)

        radioGroup = findViewById(R.id.text_switcher_radio_group)
        radioLeft = findViewById(R.id.radioLeft)
        radioRight = findViewById(R.id.radioRight)

        val attributesArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TextSwitcher)

        radioLeft.text = attributesArray.getText(R.styleable.TextSwitcher_leftText)
        radioRight.text = attributesArray.getText(R.styleable.TextSwitcher_rightText)

        leftDrawable = attributesArray.getDrawable(R.styleable.TextSwitcher_android_drawableLeft)
        rightDrawable = attributesArray.getDrawable(R.styleable.TextSwitcher_android_drawableRight)

        if (leftDrawable != null) {
            radioLeft.setCompoundDrawables(leftDrawable, null, null, null)
        }

        if (rightDrawable != null) {
            radioRight.setCompoundDrawables(null, null, rightDrawable, null)
        }

        radioRight.isChecked =
            attributesArray.getBoolean(R.styleable.TextSwitcher_rightPicked, false)
        radioLeft.isChecked = !radioRight.isChecked

        attributesArray.recycle()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioLeft) {
                checkedListener.onCheckedChange(false)
            } else if (checkedId == R.id.radioRight) {
                checkedListener.onCheckedChange(true)
            }
        }
    }

    interface OnCheckedTextChangeListener {
        fun onCheckedChange(isRightChecked: Boolean)
    }

    fun setOnCheckedChangeListener(
        checkLeft: () -> Unit = checkedLeftEvent,
        checkRight: () -> Unit = checkedRightEvent
    ) {
        checkedListener = object : OnCheckedTextChangeListener {
            override fun onCheckedChange(isRightChecked: Boolean) {
                rightChecked = when (isRightChecked) {
                    true -> {
                        checkRight()
                        true
                    }
                    else -> {
                        checkLeft()
                        false
                    }
                }
            }
        }
    }

    fun getIsRightChecked() = rightChecked

}