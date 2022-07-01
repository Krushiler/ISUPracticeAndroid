package com.bravedeveloper.sandbase.presentation.base.views

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcelable
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneTextWatcher
import com.google.android.material.textfield.TextInputEditText

class InfoItemView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    companion object {
        private const val DEFAULT_INPUT_TYPE = 17
    }
    
    private var infoItemTitle: TextView
    private var infoItemDescription: TextInputEditText
    private var divider: View
    private var isDividerVisible: Boolean
    private var isEditable: Boolean
    private var isDisabled: Boolean
    private var isHidden: Boolean
    private var inputType: Int
    private var hint: String?
    private var digits: String?
    init {
        LayoutInflater.from(context).inflate(R.layout.order_info_item, this, true)
        infoItemTitle = findViewById(R.id.info_title)
        infoItemDescription = findViewById(R.id.info_description)
        divider = findViewById(R.id.divider)
        val attributesArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InfoItemView)
        infoItemTitle.text = attributesArray.getText(R.styleable.InfoItemView_titleText)
        infoItemDescription.setText(attributesArray.getText(R.styleable.InfoItemView_descriptionText))
        infoItemDescription.maxEms =
            attributesArray.getInteger(R.styleable.InfoItemView_maxEms, Integer.MAX_VALUE)
        isDividerVisible = attributesArray.getBoolean(R.styleable.InfoItemView_dividerIsVisible, true)
        isEditable =
            attributesArray.getBoolean(R.styleable.InfoItemView_descriptionIsEditable, false)
        isDisabled = attributesArray.getBoolean(R.styleable.InfoItemView_disabledStyle, false)
        isHidden = attributesArray.getBoolean(R.styleable.InfoItemView_hiddenStyle, false)
        inputType = attributesArray.getInt(R.styleable.InfoItemView_android_inputType, DEFAULT_INPUT_TYPE)
        hint = attributesArray.getString(R.styleable.InfoItemView_android_hint)
        digits = attributesArray.getString(R.styleable.InfoItemView_android_digits)
        val textSize = attributesArray.getDimension(R.styleable.InfoItemView_descriptionTextSize, 18f)
        infoItemDescription.textSize = textSize
        setDigits(digits)
        setHint(hint)
        setDescriptionInputType(inputType)
        disableDivider(isDividerVisible)
        setEditable(isEditable)
        setDisabledStyle(isDisabled)
        setHiddenStyle(isHidden)
        attributesArray.recycle()
    }

    private fun disableDivider(isVisible: Boolean) {
        if (isVisible) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.GONE
        }
    }
    private fun setDigits(digits: String?) {
        if (digits != null) infoItemDescription.keyListener = DigitsKeyListener.getInstance(digits)
    }
    private fun setHint(hintText: String?) {
        infoItemDescription.hint = hintText
    }
    fun setPhoneNumberTextWatcher() {
        infoItemDescription.addTextChangedListener(PhoneTextWatcher(infoItemDescription))
    }

    fun setEditable(isEditable: Boolean) {
        infoItemDescription.isEnabled = isEditable
        if (isEditable) {
            setDescriptionInputType(inputType)
        } else {
            setDescriptionInputType(InputType.TYPE_NULL)
        }
    }
     fun setDisabledStyle(isDisabled: Boolean) {
        if (isDisabled) {
            infoItemDescription.setTextColor(ContextCompat.getColor(context, R.color.dark_gray))
        } else {
            infoItemDescription.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }
    private fun setHiddenStyle(isHidden: Boolean) {
        if (isHidden) {
            infoItemDescription.setTextColor(ContextCompat.getColor(context, R.color.grey3))
        } else {
            infoItemDescription.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    fun setDescription(description: String?) {
        infoItemDescription.setText(description)
    }

    fun getDescription(): String {
        return infoItemDescription.text.toString()
    }

    fun setTitle(title: String?) {
        infoItemTitle.text = title
    }

    fun getTitle(): String {
        return infoItemTitle.text.toString()
    }

    fun hideDivider(hide: Boolean) {
        divider.isVisible = !hide
    }
    private fun setDescriptionInputType(inputType: Int) {
        infoItemDescription.inputType = inputType
    }
    fun setStatus(status: OrderStatusEnum?) {
        infoItemDescription.setText(when (status) {
            OrderStatusEnum.NEW -> context.getString(R.string.active_order)
            OrderStatusEnum.IN_PROGRESS -> context.getString(R.string.exec_orders)
            OrderStatusEnum.CANCELED -> context.getString(R.string.cancelled)
            OrderStatusEnum.CLOSED -> context.getString(R.string.closed)
            OrderStatusEnum.DONE -> context.getString(R.string.done)
            OrderStatusEnum.ON_MODERATION -> context.getString(R.string.on_moderation)
            OrderStatusEnum.WAITING_FOR_CONFIRMATION -> context.getString(R.string.evaluating_orders)
            else -> context.getString(R.string.no_data)
        })
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        infoItemDescription.setOnFocusChangeListener { view, b ->
            l?.onFocusChange(view, b)
            refreshDrawableState()
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }
}