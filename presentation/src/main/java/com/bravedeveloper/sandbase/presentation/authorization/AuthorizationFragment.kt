package com.bravedeveloper.sandbase.presentation.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.authorization.sigin.SignInFragment
import com.bravedeveloper.sandbase.presentation.authorization.signup.SignUpFragment
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.databinding.FragmentAuthorizationBinding
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.util.hideKeyboard
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment :
    BaseBindingFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::inflate) {

    private val viewModel: AuthorizationViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private val listOfFragment = mutableListOf<Pair<String, Fragment>>()


    private lateinit var adapter : AuthorizationViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayoutAndViewPager()
        initViews()
        hideKeyboardOnOutboxTap()

        userViewModel.getPhone().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.authorizationTabLayout.getTabAt(0)?.view?.performClick()
            }
        }

    }

    private fun initViews() {
        binding.apply {
            includedToolbar.toolbar.apply {
                setNavigationIcon(R.drawable.ic_backspace)
                setNavigationOnClickListener { viewModel.goBack() }
            }
        }
    }
    private fun hideKeyboardOnOutboxTap() {
        binding.apply {
            authorizationLayout.setOnClickListener {
                it.hideKeyboard(context)
            }
        }
    }
    private fun createMarginBetweenTabs() {
        for (i in 0 until binding.authorizationTabLayout.tabCount) {
            val tabItem = (binding.authorizationTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val marginParams = tabItem.layoutParams as ViewGroup.MarginLayoutParams
            marginParams.marginStart = resources.getDimension(R.dimen.tab_item_spacing).toInt()
            if (i == binding.authorizationTabLayout.tabCount - 1) marginParams.marginEnd =
                marginParams.marginStart
            tabItem.requestLayout()
        }
    }

    private fun initTabLayoutAndViewPager() {

        listOfFragment.clear()
        listOfFragment.addAll(listOf(
            Pair(getString(R.string.sign_in_for_switch_button),  SignInFragment() ),
            Pair(getString(R.string.sign_up_for_switch_button),  SignUpFragment() )
        ))

        adapter = AuthorizationViewPagerAdapter(this, listOfFragment)
        binding.apply {
            authorizationViewPager.adapter = adapter
            authorizationViewPager.isUserInputEnabled = false

            TabLayoutMediator(
                authorizationTabLayout,
                authorizationViewPager,
                false,
                false
            ) { tab, position ->
                tab.text = listOfFragment[position].first
            }.attach()

            createMarginBetweenTabs()
        }
    }
}