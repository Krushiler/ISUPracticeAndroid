package com.bravedeveloper.sandbase.presentation.sellerfilter

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentSellerFilterBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

private const val CHECKBOX_MARGIN_LEFT = 29f
private const val SLIDER_MIN_VALUE = 1f
private const val SLIDER_MAX_VALUE = 1000f

@AndroidEntryPoint
class SellerFilterFragment :
    BaseBindingFragment<FragmentSellerFilterBinding>(FragmentSellerFilterBinding::inflate) {

    private lateinit var filterSpinnerAdapter: SellerFilterSpinnerAdapter

    private val sellerFilterViewModel: SellerFilterViewModel by activityViewModels()

    private var minimalValue: Int = 0
    private var maximalValue: Int = 1000

    companion object {
        fun newInstance() = SellerFilterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterSpinnerAdapter = SellerFilterSpinnerAdapter(requireContext(), getFilterTypes())

        setCargoCheckboxes()

        binding.apply {
            sellerFilterToolbarInclude.toolbar.apply {
                setNavigationIcon(R.drawable.ic_backspace)
                setNavigationOnClickListener { requireActivity().onBackPressed() }
            }

            sellerFilterToolbarInclude.toolbar.inflateMenu(R.menu.toolbar_menu_notifications)

            offerSizeRangeSlider.addOnChangeListener { slider, _, _ ->
                minimalValue = slider.values[0].toInt()
                maximalValue = slider.values[1].toInt()
                tvMin.text = minimalValue.toString()
                tvMax.text = maximalValue.toString()
            }

            offerSizeRangeSlider.values =
                listOf(offerSizeRangeSlider.valueFrom, offerSizeRangeSlider.valueTo)

            sellerFilterTypeSP.adapter = filterSpinnerAdapter

            resetSellerFilterBTN.setOnClickListener {
                sellerFilterTypeSP.adapter = filterSpinnerAdapter
                offerSizeRangeSlider.setValues(SLIDER_MIN_VALUE, SLIDER_MAX_VALUE)

                for (index in 0 until filterCheckboxLayout.childCount) {
                    val checkBox = filterCheckboxLayout[index]
                    if (checkBox is CheckBox) {
                        checkBox.isChecked = false
                    }
                }
            }

            showSellerFilterBTN.setOnClickListener {
                sellerFilterViewModel.applyFilter(null, null, minimalValue, maximalValue)
                sellerFilterViewModel.navigateBack()
            }
        }
    }

    private fun setCargoCheckboxes() {
        sellerFilterViewModel.getCargoTypesLiveData().observe(viewLifecycleOwner) {
            for (cargo in it.cargo) {
                val checkBox = CheckBox(requireContext(), null)
                checkBox.text = cargo.type
                checkBox.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                val colorStateList = ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_checked),
                        intArrayOf(android.R.attr.state_checked)
                    ), intArrayOf(
                        ContextCompat.getColor(requireContext(), R.color.grey3), //unchecked color
                        ContextCompat.getColor(requireContext(), R.color.orange_light) //checked color
                    )
                )
                checkBox.buttonTintList = colorStateList

                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val marginStart = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, CHECKBOX_MARGIN_LEFT, resources
                        .displayMetrics
                ).toInt()
                params.setMargins(marginStart, 0, 0, 0)
                checkBox.layoutParams = params

                binding.filterCheckboxLayout.addView(checkBox)
            }
        }
        sellerFilterViewModel.getCargoTypes()
    }

    private fun getFilterTypes(): List<FilterTypeItem> {
        return listOf(
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_price)),
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_rating)),
            FilterTypeItem(requireActivity().resources.getString(R.string.order_sort_method_popularity)),
        )
    }
}