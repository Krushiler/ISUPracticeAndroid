package com.bravedeveloper.sandbase.presentation.settings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SettingsTabAdapter(
    fragment: Fragment,
    private val fragmentList: MutableList<SettingFragmentItem>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].fragment
    }

}