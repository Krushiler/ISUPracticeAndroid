package com.bravedeveloper.sandbase.presentation.settings

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentSettingsBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.ViewPagerResizeExtension
import com.bravedeveloper.sandbase.presentation.mainactivity.MainActivity
import com.bravedeveloper.sandbase.presentation.settings.companydata.CompanyDataFragment
import com.bravedeveloper.sandbase.presentation.settings.myaccount.MyAccountFragment
import com.bravedeveloper.sandbase.presentation.settings.settingsnotifications.SettingsNotificationFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel: SettingsViewModel by activityViewModels()
    private val viewPagerViews: MutableList<SettingFragmentItem> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_burger)
            setNavigationOnClickListener { (activity as MainActivity).openDrawer() }
        }

        createTabLayoutFromFragmentList(getPhysicalFaceFragments())

        binding.apply {
            settingsFaceSwitch.setOnCheckedChangeListener(
                {
                    createTabLayoutFromFragmentList(getPhysicalFaceFragments())
                }, {
                    createTabLayoutFromFragmentList(getJuristicFaceFragments())
                }
            )
            editDataButton.setOnClickListener {
                changeEditable(isEditable = true)
            }
            saveButton.setOnClickListener {
                viewModel.setSaved()
                changeEditable(isEditable = false)
            }
            undoChangesButton.setOnClickListener {
                viewModel.setCanceled()
                changeEditable(isEditable = false)
            }
        }

        ViewPagerResizeExtension(this.view, binding.settingsViewPager, viewPagerViews)

    }

    private fun createMarginBetweenTabs() {
        for (i in 1 until binding.settingsTabLayout.tabCount) {
            val tabItem = (binding.settingsTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val marginParams = tabItem.layoutParams as ViewGroup.MarginLayoutParams
            marginParams.marginStart = resources.getDimension(R.dimen.tab_item_spacing).toInt()
            tabItem.requestLayout()
        }
    }

    private fun getJuristicFaceFragments(): MutableList<SettingFragmentItem> {
        viewPagerViews.clear()
        val settingFragments = mutableListOf(
            SettingFragmentItem(MyAccountFragment(), resources.getString(R.string.my_account)),
            SettingFragmentItem(CompanyDataFragment(), resources.getString(R.string.company_data)),
            SettingFragmentItem(SettingsNotificationFragment(), resources.getString(R.string.notification_setting)
            )
        )
        viewPagerViews.addAll(settingFragments)
        return settingFragments
    }

    private fun getPhysicalFaceFragments(): MutableList<SettingFragmentItem> {
        viewPagerViews.clear()
        val settingFragments = mutableListOf(
            SettingFragmentItem(MyAccountFragment(), resources.getString(R.string.my_account)),
            SettingFragmentItem(SettingsNotificationFragment(), resources.getString(R.string.notification_setting)
            )
        )
        viewPagerViews.addAll(settingFragments)
        return settingFragments
    }

    private fun createTabLayoutFromFragmentList(fragmentList: MutableList<SettingFragmentItem>) {
        val viewPagerAdapter = SettingsTabAdapter(this, fragmentList)
        binding.settingsViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.settingsTabLayout, binding.settingsViewPager) { tab, position ->
            tab.text = fragmentList[position].title
        }.attach()
        createMarginBetweenTabs()
    }

    private fun changeEditable(isEditable: Boolean) {
        if (isEditable) viewModel.setEditable() else viewModel.setNonEditable()
        binding.editDataButton.visibility = if (isEditable) View.GONE else View.VISIBLE
        binding.undoChangesButton.visibility = if (isEditable) View.VISIBLE else View.GONE
        binding.saveButton.visibility = if (isEditable) View.VISIBLE else View.GONE
    }
}