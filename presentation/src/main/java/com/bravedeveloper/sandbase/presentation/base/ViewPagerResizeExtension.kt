package com.bravedeveloper.sandbase.presentation.base

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.bravedeveloper.sandbase.presentation.settings.SettingFragmentItem

class ViewPagerResizeExtension(private val fragmentView : View?, private val viewPager2: ViewPager2, private val screens : List<SettingFragmentItem>) {

    init {
        makeViewPagerResizable()
    }

    private fun makeViewPagerResizable() {

        val resizeListener = ViewTreeObserver.OnGlobalLayoutListener {
            val viewToUpdate : View? = screens[viewPager2.currentItem].fragment.view
            if (fragmentView != null && viewToUpdate != null) {
                updatePagerHeightForChild(viewToUpdate)
            }
        }

        viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                screens.forEach { it.fragment.view?.viewTreeObserver?.removeOnGlobalLayoutListener(resizeListener) }
                Handler(Looper.getMainLooper()).post{
                    if (screens.isNotEmpty()) {
                        screens[position].fragment.view?.viewTreeObserver?.addOnGlobalLayoutListener(
                            resizeListener
                        )
                    }
                    viewPager2.invalidate()
                    viewPager2.requestLayout()
                }
            }
        })
    }

    private fun updatePagerHeightForChild(view: View) {
        view.post {
            val wMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.UNSPECIFIED)
            view.measure(wMeasureSpec, hMeasureSpec)

            if (viewPager2.layoutParams.height != view.measuredHeight) {
                viewPager2.layoutParams =
                    (viewPager2.layoutParams as ConstraintLayout.LayoutParams)
                        .also { lp -> lp.height = view.measuredHeight }
            }
        }
    }

}