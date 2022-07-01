package com.bravedeveloper.sandbase.presentation.orderinfo.infocustomer.details

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingResponseBinding
import com.bravedeveloper.sandbase.databinding.FragmentOrderInfoBuyerDetailsBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.util.getTitle
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderInfoBuyerDetailsFragment : BaseBindingFragment<FragmentOrderInfoBuyerDetailsBinding>(FragmentOrderInfoBuyerDetailsBinding::inflate) {

    private val viewModel: OrderInfoViewModel by activityViewModels()

    private lateinit var cancelButtonLayout: FrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButtonLayout = binding.cancelButtonLayout

        viewModel.getOrderLiveData().observe(viewLifecycleOwner) {
            binding.apply {
                orderInfoStatus.setStatus(it.order.status)
                orderInfoMaterialAndVolume.setDescription(it.order.getTitle(resources))
                orderInfoDateAndTime.setDescription(it.order.date.toString() + ", " + it.order.time)
                orderInfoAddress.setDescription(it.order.destination.toString())
                if (it.order.comment != null) {
                    orderInfoCommentary.visibility = View.VISIBLE
                    orderInfoCommentary.setDescription(it.order.comment.toString())
                } else {
                    orderInfoCommentary.visibility = View.GONE
                    orderInfoAddress.hideDivider(true)
                }
            }

            if (it.orderType.orderInfoType == OrderStatusEnum.NEW || it.orderType.orderInfoType == OrderStatusEnum.IN_PROGRESS) {
                cancelButtonLayout.visibility = View.VISIBLE
                binding.orderInfoCancelOrderButton.setOnClickListener {
                    showOrderActionDialog(resources.getString(R.string.remove_order), resources.getString(R.string.do_you_really_want_to_remove_order)) {
                        viewModel.removeOrder()
                    }
                }
            } else if (it.orderType.orderInfoType == OrderStatusEnum.CANCELED) {
                cancelButtonLayout.visibility = View.VISIBLE
                binding.orderInfoCancelOrderButton.setOnClickListener {
                    showOrderActionDialog(resources.getString(R.string.delete_order), resources.getString(R.string.do_you_really_want_to_delete_order)) {
                        viewModel.removeOrder()
                    }
                }
            }
            else {
                cancelButtonLayout.visibility = View.GONE
            }

        }
    }

    private inline fun showOrderActionDialog(title: String, description: String, crossinline action: () -> Unit) {
        binding.apply {
            val dialogDeleteResponseReply = AlertDialogDeletingResponseBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogDeleteResponseReply.root)
                .create()

            dialogDeleteResponseReply.apply {
                deletingResponseText.text = title
                doYouReallyWantToDeleteResponseText.text = description
            }

            dialogDeleteResponseReply.cancellationCrossImageButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogDeleteResponseReply.cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            dialogDeleteResponseReply.confirmationDeletingResponseButton.setOnClickListener {
                action.invoke()
                dialog.cancel()
            }
            dialog.show()
        }
    }

}