package com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderDetailsBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.util.getOrderFullTime
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItem
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItemListAdapter
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItemType
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseBindingFragment<FragmentOrderDetailsBinding>(FragmentOrderDetailsBinding::inflate),
    OrderInfoItemListAdapter.AddressClickListener {

    private val viewModel: OrderInfoViewModel by activityViewModels()
    private val orderInfoAdapter = CompositeDelegateAdapter(OrderInfoItemListAdapter(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initSwipeRefreshLayout()

        viewModel.getOrderLiveData().observe(viewLifecycleOwner) {
            var rating = 0
            it.order.customer?.rating?.let { stars -> rating = stars }
            orderInfoAdapter.swapData(
                listOf(
                    OrderInfoItem(R.string.status, "Новая заявка", OrderInfoItemType.DEFAULT),
                    OrderInfoItem(
                        R.string.material_and_volume,
                        it.order.getTitle(resources),
                        OrderInfoItemType.DEFAULT
                    ),
                    OrderInfoItem(
                        R.string.date_and_time,
                        if (it.order.time != null && it.order.date != null) {
                            getOrderFullTime(it.order.date.toString(), it.order.time!!, resources)
                        } else {
                            ""
                        },
                        OrderInfoItemType.DEFAULT
                    ),
                    OrderInfoItem(
                        R.string.address,
                        it.order.destination.toString(),
                        OrderInfoItemType.ADDRESS
                    ),
                    OrderInfoItem(R.string.rating, rating, type = OrderInfoItemType.RATING),
                    OrderInfoItem(
                        R.string.phone,
                        it.order.customer?.phone,
                        type = OrderInfoItemType.PHONE
                    ),
                    OrderInfoItem(
                        R.string.comment,
                        it.order.comment,
                        type = OrderInfoItemType.COMMENTARY
                    ),
                )
            )
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.apply {
            orderDetailsSwipeRefresh.setOnRefreshListener {
                orderDetailsSwipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            orderInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderInfoRecyclerView.adapter = orderInfoAdapter
        }
    }

    override fun onAddressClick() {

    }
}