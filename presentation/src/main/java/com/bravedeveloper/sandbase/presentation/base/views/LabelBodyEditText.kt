package com.bravedeveloper.sandbase.presentation.base.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R
import com.google.android.material.textfield.TextInputLayout


class LabelBodyEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val CODE_MULTILINE = 131073

    val labelTextView: TextInputLayout
    val bodyEditText: EditText

    private var editBackground: Drawable?
    private var editTextColor: Int
    private var defaultBackground: Drawable?
    private var defaultTextColor: Int
    private var inputType: Int

    private val STATE_FOCUSED = intArrayOf(R.styleable.LabelBodyEditText_android_state_focused)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_label_body_edit_text, this, true)

        labelTextView = findViewById(R.id.text_field_title)
        bodyEditText = findViewById(R.id.text_field_body)

        //region Getting Attributes and Setup

        val attributesArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LabelBodyEditText)

        labelTextView.hint = attributesArray.getText(R.styleable.LabelBodyEditText_labelText)
        bodyEditText.setText(attributesArray.getText(R.styleable.LabelBodyEditText_bodyText))
        defaultBackground =
            attributesArray.getDrawable(R.styleable.LabelBodyEditText_android_background)
        background = defaultBackground

        inputType = attributesArray.getInt(R.styleable.LabelBodyEditText_android_inputType, 17)

        bodyEditText.inputType = inputType

        labelTextView.hintTextColor = (
                attributesArray.getColorStateList(
                    R.styleable.LabelBodyEditText_android_textColorHint
                )
                )

        defaultTextColor = attributesArray.getColor(
            R.styleable.LabelBodyEditText_android_textColor,
            ContextCompat.getColor(context, R.color.black)
        )

        bodyEditText.setTextColor(defaultTextColor)

        bodyEditText.textSize = attributesArray.getDimension(
            R.styleable.LabelBodyEditText_android_textSize,
            resources.getDimension(R.dimen.text_18)
        )

        editBackground = attributesArray.getDrawable(R.styleable.LabelBodyEditText_editBackground)
        editTextColor = attributesArray.getColor(
            R.styleable.LabelBodyEditText_editTextColor,
            ContextCompat.getColor(context, R.color.black)
        )

        attributesArray.recycle()

        labelTextView.endIconMode = TextInputLayout.END_ICON_NONE
        if (inputType == 18 || inputType == 129) {
            labelTextView.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }

        //endregion
        bodyEditText.setOnFocusChangeListener { view, b -> refreshDrawableState() }
        setEditable(false)
    }

    fun setEditable(isEditable: Boolean) {
        if (isEditable) {
            bodyEditText.setTextColor(editTextColor)
            background = editBackground
            bodyEditText.isFocusableInTouchMode = true
            bodyEditText.inputType = inputType
            setOnClickListener {
                bodyEditText.requestFocus()
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(bodyEditText, InputMethodManager.SHOW_IMPLICIT)
            }
        } else {
            bodyEditText.setTextColor(defaultTextColor)
            background = defaultBackground
            bodyEditText.isFocusable = false
            bodyEditText.inputType = InputType.TYPE_NULL
            if (inputType == CODE_MULTILINE) {
                bodyEditText.isSingleLine = false
            }
            setOnClickListener(null)
        }
    }

    override fun isFocused() = bodyEditText.isFocused

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (bodyEditText.isFocused) mergeDrawableStates(drawableState, STATE_FOCUSED)
        return drawableState
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        bodyEditText.setOnFocusChangeListener { view, b ->
            l?.onFocusChange(view, b)
            refreshDrawableState()
        }
    }

    fun setBodyText(text: String) {
        bodyEditText.setText(text)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

}