package com.bravedeveloper.sandbase.presentation.orderinfo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderInfoViewPagerAdapter(
    container: Fragment,
    private val elements: List<Pair<String, () -> Fragment>>
) : FragmentStateAdapter(container) {

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun createFragment(position: Int): Fragment {
        return elements[position].second.invoke()
    }
}
