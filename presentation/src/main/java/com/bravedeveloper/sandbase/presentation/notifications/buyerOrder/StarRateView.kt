package com.bravedeveloper.sandbase.presentation.notifications.buyerOrder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bravedeveloper.sandbase.R

class StarRateView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var rating: Int = 0

    private var stars: List<ImageView>

    private var starsCount: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_stars_view, this, true)

        stars = listOf(
            view.findViewById(R.id.star1),
            view.findViewById(R.id.star2),
            view.findViewById(R.id.star3),
            view.findViewById(R.id.star4),
            view.findViewById(R.id.star5)
        )
        starsCount = view.findViewById(R.id.number_of_stars_tv)

        val attributesArray = context.obtainStyledAttributes(attrs, R.styleable.StarRateView)

        rating = attributesArray.getInt(R.styleable.StarRateView_rating, 0)

        updateRating(rating)

        attributesArray.recycle()
    }

    private fun updateRating(rating: Int) {
        for (i in 0 until rating) {
            stars[i].setImageResource(R.drawable.ic_star)
        }
        if (starsCount.text.isNotEmpty()) starsCount.text.replaceRange(0, 1, rating.toString())
    }

    fun setRating(rating: Int) {
        updateRating(rating)
    }

}