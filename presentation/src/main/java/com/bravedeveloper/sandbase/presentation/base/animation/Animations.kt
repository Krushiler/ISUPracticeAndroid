package com.bravedeveloper.sandbase.presentation.base.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun showViewFade(view: View, duration: Long) {
    view.apply {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(null)
    }
}

fun hideViewFade(view: View, duration: Long) {
    view.apply {
        animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.GONE
                }
            })
    }
}