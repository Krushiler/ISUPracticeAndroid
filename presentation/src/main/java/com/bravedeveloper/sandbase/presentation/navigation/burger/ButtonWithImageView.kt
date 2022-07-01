package com.bravedeveloper.sandbase.presentation.navigation.burger

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bravedeveloper.sandbase.R

class ButtonWithImageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var textBtn: TextView
    private var image: ImageView

    private var defaultTextColor: Int
    private var defaultImage: Drawable
    private var defaultText: String
    private var defaultGravity: Int


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_button_with_image_view, this, true)

        textBtn = findViewById(R.id.text)
        image = findViewById(R.id.image)

        val attributesArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonWithImageView)

        defaultTextColor = attributesArray.getColor(R.styleable.ButtonWithImageView_android_textColor, ContextCompat.getColor(context, R.color.grey4))
        defaultImage = attributesArray.getDrawable(R.styleable.ButtonWithImageView_android_src)!!
        defaultText = attributesArray.getString(R.styleable.ButtonWithImageView_android_text)!!
        defaultGravity = attributesArray.getInt(R.styleable.ButtonWithImageView_android_gravity, Gravity.START)

        textBtn.setText(defaultText)
        textBtn.setTextColor(defaultTextColor)
        image.setImageDrawable(defaultImage)

        gravity = defaultGravity
    }


}