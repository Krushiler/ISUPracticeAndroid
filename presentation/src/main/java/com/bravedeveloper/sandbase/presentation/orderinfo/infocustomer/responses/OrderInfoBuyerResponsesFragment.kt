package com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.DialogApproveReplyBinding
import com.bravedeveloper.sandbase.databinding.FragmentOrderInfoBuyerResponsesBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderList.OrderItemDecoration
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.responsesrecycler.OrderInfoBuyerResponseAdapter
import com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.responses.responsesrecycler.OrderInfoBuyerResponseItem
import com.bravedeveloper.sandbase.presentation.sellerfilter.FilterTypeItem
import com.bravedeveloper.sandbase.presentation.sellerfilter.SellerFilterSpinnerAdapter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderInfoBuyerResponsesFragment : BaseBindingFragment<FragmentOrderInfoBuyerResponsesBinding>(
    FragmentOrderInfoBuyerResponsesBinding::inflate
) {

    private val responses = mutableListOf<OrderInfoBuyerResponseItem>()
    private lateinit var responsesAdapter: CompositeDelegateAdapter
    private val viewModel: OrderInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        responsesAdapter =
            CompositeDelegateAdapter(OrderInfoBuyerResponseAdapter(requireContext()) {
                approveReply(it)
            })

        binding.apply {
            val filterSpinnerAdapter =
                SellerFilterSpinnerAdapter(requireActivity(), getFilterTypes())
            orderInfoBuyerSellerFilterTypeSP.adapter = filterSpinnerAdapter
            orderInfoBuyerResponsesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            orderInfoBuyerResponsesRecyclerView.adapter = responsesAdapter
            orderInfoBuyerResponsesRecyclerView.addItemDecoration(
                OrderItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.normal_16
                    ), 0
                )
            )
        }


        viewModel.getOrderLiveData().observe(viewLifecycleOwner) {
            responses.clear()
            it.order.replies?.map { reply ->
                responses.add(
                    OrderInfoBuyerResponseItem(
                        reply.id.toString(),
                        reply.seller?.avatar?.formats?.small?.url,
                        reply.seller?.name.toString(),
                        reply.seller?.rating,
                        reply.seller?.verified == true,
                        true,
                        reply.seller?.entrepreneur?.active == true,
                        reply.comment,
                        reply.price.toString(),
                        reply.seller?.phone.toString(),
                        reply.approved == true,
                        it.order.status,
                        it.order.id.orEmpty()
                    )
                )
            }
            responsesAdapter.swapData(responses)
        }

    }

    private fun getFilterTypes(): List<FilterTypeItem> {
        return listOf(
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_price)),
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_rating)),
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_popularity)),
        )
    }

    private fun approveReply(reply: OrderInfoBuyerResponseItem) {
        showApproveReplyDialog(reply)
    }

    private fun showApproveReplyDialog(reply: OrderInfoBuyerResponseItem) {
        binding.apply {
            val dialogCreateReply = DialogApproveReplyBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogCreateReply.root)
                .create()

            dialogCreateReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogCreateReply.confirmationButton.setOnClickListener {
                viewModel.approveReply(
                    reply.orderId,
                    reply.id
                )
                dialog.cancel()
            }

            dialogCreateReply.commentaryTextView.text = reply.commentary
            dialogCreateReply.priceTextView.text =
                "${reply.price} ${resources.getString(R.string.rubbles_short)}"

            dialog.show()
        }
    }

}