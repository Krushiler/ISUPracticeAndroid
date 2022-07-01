package com.bravedeveloper.sandbase.presentation.orderinfo


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.MarginPageTransformer
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderInfoBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.details.OrderInfoBuyerDetailsFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.OrderInfoBuyerResponsesFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderdetails.OrderDetailsFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderresponses.OrderResponseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderInfoFragment :
    BaseBindingFragment<FragmentOrderInfoBinding>(FragmentOrderInfoBinding::inflate) {

    private val orderInfoViewModel: OrderInfoViewModel by activityViewModels()
    private val listOfFragment = mutableListOf<Pair<String, () -> Fragment>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        orderInfoViewModel.getOrderLiveData().observe(viewLifecycleOwner) {
            if (it.orderType.userRoleEnum == UserRoleEnum.CUSTOMER) {
                getCustomerFragments(it.orderType.hasResponses)
            } else {
                getSellerFragments(it.orderType.hasMyResponse)
            }
            initTabsAndViewPager(listOfFragment)
        }
    }

    private fun initToolbar() {
        binding.orderInfoToolbar.toolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setNavigationIcon(R.drawable.ic_backspace)
        }
    }

    private fun initTabsAndViewPager(fragments: List<Pair<String, () -> Fragment>>) {
        val adapter = OrderInfoViewPagerAdapter(this, fragments)
        binding.orderInfoViewPager.adapter = adapter
        binding.orderInfoViewPager.setPageTransformer(
            MarginPageTransformer(
                resources.getDimension(R.dimen.normal_12).toInt()
            )
        )
        TabLayoutMediator(binding.orderInfoTabLayout, binding.orderInfoViewPager) { tab, position ->
            tab.text = listOfFragment[position].first
        }.attach()
    }

    private fun getSellerFragments(hasSellerResponse: Boolean) {
        listOfFragment.clear()
        listOfFragment.add(getString(R.string.details) to { OrderDetailsFragment() })
        if (hasSellerResponse) listOfFragment.add(
            getString(R.string.response)
                    to
                    { OrderResponseFragment() })
    }

    private fun getCustomerFragments(hasResponses: Boolean) {
        listOfFragment.clear()
        listOfFragment.add(getString(R.string.details) to { OrderInfoBuyerDetailsFragment() })
        if (hasResponses) listOfFragment.add(
            getString(R.string.responses)
                    to
                    { OrderInfoBuyerResponsesFragment() })
    }

}