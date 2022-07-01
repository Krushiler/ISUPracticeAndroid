package com.bravedeveloper.sandbase.presentation.settings.companydata

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentCompanyDataBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.animation.hideViewFade
import com.bravedeveloper.sandbase.presentation.base.animation.showViewFade
import com.bravedeveloper.sandbase.presentation.base.views.DividerItemDecorationWithoutLastElement
import com.bravedeveloper.sandbase.presentation.settings.SettingsViewModel
import com.bravedeveloper.sandbase.presentation.settings.general.EditState
import com.bravedeveloper.sandbase.presentation.settings.general.SettingsItem
import com.bravedeveloper.sandbase.presentation.settings.general.SettingsTextInputItemDecoration
import com.bravedeveloper.sandbase.presentation.settings.general.settingstextinput.SettingsTextInputAdapter
import com.bravedeveloper.sandbase.presentation.settings.general.settingstextinput.SettingsTextInputItem
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CompanyDataFragment :
    BaseBindingFragment<FragmentCompanyDataBinding>(FragmentCompanyDataBinding::inflate) {

    companion object {
        const val ID_NAME_SURNAME = 0
        const val ID_COMPANY_NAME = 1
        const val ID_ADDRESS = 2

        const val ID_INN = 0
        const val ID_KPP = 1
        const val ID_ORGN = 2
        const val ID_NDS = 3
    }

    private val settingsItems = mutableListOf<SettingsItem>()
    private val settingsBankItems = mutableListOf<SettingsItem>()

    private val viewModel: SettingsViewModel by activityViewModels()

    private var settingsAdapter = CompositeDelegateAdapter()
    private var settingsBankAdapter = CompositeDelegateAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapters()

        viewModel.getEditState().observe(viewLifecycleOwner) { editable ->
            if (editable == EditState.Editing) setEditable()
            else setNonEditable()
        }
    }

    private fun setUpAdapters() {
        settingsItems.clear()
        settingsItems.addAll(
            listOf(
                SettingsTextInputItem(
                    itemID = ID_NAME_SURNAME, resources.getString(R.string.full_name_head), ""
                ),
                SettingsTextInputItem(
                    itemID = ID_COMPANY_NAME, resources.getString(R.string.company_name), ""
                ),
                SettingsTextInputItem(
                    itemID = ID_ADDRESS, resources.getString(R.string.company_adress), ""
                )
            )
        )

        settingsBankItems.clear()
        settingsBankItems.addAll(
            listOf(
                SettingsTextInputItem(
                    itemID = ID_INN, resources.getString(R.string.inn), ""
                ),
                SettingsTextInputItem(
                    itemID = ID_KPP, resources.getString(R.string.kpp), ""
                ),
                SettingsTextInputItem(
                    itemID = ID_ORGN, resources.getString(R.string.orgn), ""
                ),
                SettingsTextInputItem(
                    itemID = ID_NDS, resources.getString(R.string.nds), ""
                )
            )
        )

        settingsAdapter = CompositeDelegateAdapter(SettingsTextInputAdapter { item, text ->
            item.text = text
        })
        settingsBankAdapter = CompositeDelegateAdapter(SettingsTextInputAdapter { item, text ->
            item.text = text
        })

        settingsAdapter.swapData(settingsItems)
        settingsBankAdapter.swapData(settingsBankItems)

        val dividerItemDecoration = DividerItemDecorationWithoutLastElement(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.settings_item_divider
            )
        )
        binding.apply {
            recyclerWithText.apply {
                adapter = settingsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    SettingsTextInputItemDecoration(
                        resources.getDimension(R.dimen.normal_16)
                            .roundToInt()
                    )
                )
                addItemDecoration(dividerItemDecoration)
            }
            recyclerViewBank.apply {
                adapter = settingsBankAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    SettingsTextInputItemDecoration(
                        resources.getDimension(R.dimen.normal_16)
                            .roundToInt()
                    )
                )
                addItemDecoration(dividerItemDecoration)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setEditable() {
        settingsItems.forEach {
            it.isEnabled = true
        }
        settingsBankItems.forEach {
            it.isEnabled = true
        }

        showViewFade(binding.recyclerViewBank, 300)

        settingsAdapter.notifyDataSetChanged()
        settingsBankAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setNonEditable() {
        settingsItems.forEach {
            it.isEnabled = false
        }
        settingsBankItems.forEach {
            it.isEnabled = false
        }

        hideViewFade(binding.recyclerViewBank, 300)

        settingsAdapter.notifyDataSetChanged()
        settingsBankAdapter.notifyDataSetChanged()
    }

}