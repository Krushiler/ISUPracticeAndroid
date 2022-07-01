package com.bravedeveloper.sandbase.presentation.orderList

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrdersTabAdapter(
    fragment: Fragment,
    private val fragmentList: MutableList<OrdersFragmentItem>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position].fragment

}