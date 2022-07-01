package com.bravedeveloper.sandbase.presentation.notifications


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogSellerRespondBinding
import com.bravedeveloper.sandbase.databinding.DialogApproveReplyBinding
import com.bravedeveloper.sandbase.databinding.FragmentNotificationsBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.notifications.affordedOrder.AffordedOrderAdapter
import com.bravedeveloper.sandbase.presentation.notifications.buyerOrder.NewNotifAdapter
import com.bravedeveloper.sandbase.presentation.notifications.buyerOrder.NewNotifItem
import com.bravedeveloper.sandbase.presentation.orderList.OrderItemDecoration
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
    BaseBindingFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val orderInfoViewModel: OrderInfoViewModel by activityViewModels()

    private var notifyOrderAdapter = CompositeDelegateAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notifyOrderAdapter = CompositeDelegateAdapter(AffordedOrderAdapter(requireContext()) {
            it.orderNumber?.let { number -> goToOrder(number) }
        }, NewNotifAdapter(requireContext(), {
            it.orderNumber?.let { number -> goToOrder(number) }
        }, {
            approveReply(it)
        }))

        notificationsViewModel.getReplies()

        notificationsViewModel.getRepliesLiveData().observe(viewLifecycleOwner) {
            binding.noNotifLayout.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            notifyOrderAdapter.swapData(it)
            notificationsViewModel.markAllAsRead(it)
        }
        binding.settingsToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_backspace)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        binding.notificationsRecyclerView.adapter = notifyOrderAdapter
        binding.notificationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationsRecyclerView.addItemDecoration(
            OrderItemDecoration(
                resources.getDimensionPixelSize(R.dimen.normal_24),
                resources.getDimensionPixelSize(R.dimen.horizontal_offer_item_margin)
            )
        )

        binding.notificationsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    notificationsViewModel.loadMore()
                }
            }
        })

    }

    private fun goToOrder(number: Int) {
        orderInfoViewModel.getOrder(number)
        notificationsViewModel.goToOrderInfoScreen()
    }

    private fun approveReply(notification: NewNotifItem) {
        showApproveReplyDialog(notification)
    }

    private fun showApproveReplyDialog(notification: NewNotifItem) {
        binding.apply {
            val dialogCreateReply = DialogApproveReplyBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogCreateReply.root)
                .create()

            dialogCreateReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogCreateReply.confirmationButton.setOnClickListener {
                notificationsViewModel.approveReply(
                    notification.orderId,
                    notification.replyId
                )
                dialog.cancel()
            }

            dialogCreateReply.commentaryTextView.text = notification.sellerComment
            dialogCreateReply.priceTextView.text =
                "${notification.price} ${resources.getString(R.string.rubbles_short)}"

            dialog.show()
        }
    }

}