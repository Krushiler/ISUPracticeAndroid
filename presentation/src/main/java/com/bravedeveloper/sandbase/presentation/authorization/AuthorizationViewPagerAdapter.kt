package com.bravedeveloper.sandbase.presentation.authorization

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthorizationViewPagerAdapter(
    fragment: Fragment,
    private val fragments: List<Pair<String, Fragment>>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].second
    }
}