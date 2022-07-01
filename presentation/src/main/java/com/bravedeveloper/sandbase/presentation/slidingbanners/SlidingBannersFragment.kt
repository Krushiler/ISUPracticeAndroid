package com.bravedeveloper.sandbase.presentation.slidingbanners

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentSlidingBannersBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlidingBannersFragment :
    BaseBindingFragment<FragmentSlidingBannersBinding>(FragmentSlidingBannersBinding::inflate) {

    private val viewModel: SlidingBannerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bannerList = createBannerList()

        val adapter = SlidingBannersAdapter(bannerList)
        initViewPagerAndCircleIndicator(adapter)

        binding.apply {
            var currentPagePosition = 1
            val lastPagePosition = bannerList.size - 1

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    currentPagePosition = position

                    checkPagePositionForPageChangeCallback(currentPagePosition, lastPagePosition)
                }
            })

            nextBannerButton.setOnClickListener {
                currentPagePosition++
                checkPagePositionForNextBannerButton(currentPagePosition, lastPagePosition)
            }

            skipBannersText.setOnClickListener {
                viewModel.goToOrderListScreen()
            }
        }
    }

    private fun createBannerList(): List<SlidingBannerItem> {
        return listOf(
            SlidingBannerItem(R.drawable.ic_sliding_image_1, R.string.sliding_text_1),
            SlidingBannerItem(R.drawable.ic_sliding_image_2, R.string.sliding_text_2),
            SlidingBannerItem(R.drawable.ic_sliding_image_3, R.string.sliding_text_3),
            SlidingBannerItem(R.drawable.ic_sliding_image_4, R.string.sliding_text_4)
        )
    }

    private fun initViewPagerAndCircleIndicator(adapter: SlidingBannersAdapter) {
        binding.apply {
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.adapter = adapter
            circleIndicator.setViewPager(viewPager)
        }
    }

    private fun checkPagePositionForPageChangeCallback(
        currentPagePosition: Int,
        lastPagePosition: Int
    ) {
        binding.apply {
            if (currentPagePosition == lastPagePosition) {
                skipBannersText.visibility = View.INVISIBLE
                nextBannerButton.setText(R.string.start)
            } else {
                skipBannersText.visibility = View.VISIBLE
                nextBannerButton.setText(R.string.next)
            }
        }
    }

    private fun checkPagePositionForNextBannerButton(
        currentPagePosition: Int,
        lastPagePosition: Int
    ) {
        binding.apply {
            if (currentPagePosition <= lastPagePosition) viewPager.currentItem = currentPagePosition
            else viewModel.goToOrderListScreen()
        }
    }
}