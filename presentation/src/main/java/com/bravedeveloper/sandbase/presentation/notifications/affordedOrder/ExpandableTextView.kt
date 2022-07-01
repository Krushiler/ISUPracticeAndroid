package com.bravedeveloper.sandbase.presentation.notifications.affordedOrder

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R

private const val trimLength = 60
private const val ellipsis = "... "

class ExpandableTextView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {


    private val expandString = resources.getString(R.string.expand)

    private var originalText: CharSequence = ""
    private var trimmedText: CharSequence = ""
    private var needTrim = true

    private val expandableSpan = SpannableString(expandString)
    private val expandClickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            expandTextView()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, R.color.orange_dark)
            ds.isUnderlineText = false
            ds.isFakeBoldText = true
        }
    }


    init {

        expandableSpan.setSpan(
            expandClickableSpan,
            0,
            expandString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        movementMethod = LinkMovementMethod.getInstance()
        originalText = text.toString()
        trimmedText = getTrimmedText()
        text = getTrimmedText()
        Log.d("SHIT", getTrimmedText().toString())
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text.toString()
        trimmedText = getTrimmedText()
        if (needTrim) {
            super.setText(trimmedText, type)
        } else {
            super.setText(originalText, type)
        }
    }

    private fun getTrimmedText(): CharSequence {
        return if (originalText.length > trimLength) {
            SpannableStringBuilder(originalText, 0, trimLength + 1).append(ellipsis)
                .append(expandableSpan)
        } else {
            originalText
        }
    }

    private fun expandTextView() {
        needTrim = false
        text = originalText
    }
}