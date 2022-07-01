package com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderresponses

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingResponseBinding
import com.bravedeveloper.sandbase.databinding.FragmentOrderResponseBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItem
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItemListAdapter
import com.bravedeveloper.sandbase.presentation.orderinfo.common.OrderInfoItemType
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderResponseFragment :
    BaseBindingFragment<FragmentOrderResponseBinding>(FragmentOrderResponseBinding::inflate) {

    private val viewModel: OrderInfoViewModel by activityViewModels()
    private val orderInfoListAdapter = CompositeDelegateAdapter(OrderInfoItemListAdapter())

    private var orderId = ""
    private var replyId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getMyReplyLiveData().observe(viewLifecycleOwner) {
            orderId = it.orderId
            replyId = it.replyId
            orderInfoListAdapter.swapData(
                listOf(
                    OrderInfoItem(
                        R.string.surname_name_patronymic,
                        it.name,
                        OrderInfoItemType.DEFAULT
                    ),
                    OrderInfoItem(
                        R.string.price,
                        it.price.toString() + " " + resources.getString(R.string.rubbles_short),
                        OrderInfoItemType.DEFAULT
                    ),
                    OrderInfoItem(
                        R.string.my_comment,
                        it.comment,
                        type = OrderInfoItemType.COMMENTARY
                    )
                )
            )
        }
    }

    private fun initViews() {
        binding.apply {
            orderResponseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderResponseRecyclerView.adapter = orderInfoListAdapter
        }
    }
}