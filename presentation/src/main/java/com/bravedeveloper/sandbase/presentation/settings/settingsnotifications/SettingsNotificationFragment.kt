package com.bravedeveloper.sandbase.presentation.settings.settingsnotifications

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.domain.model.city.CitySuggestionsData
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentSettingsNotificationBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.settings.SettingsViewModel
import com.bravedeveloper.sandbase.presentation.settings.general.EditState
import com.bravedeveloper.sandbase.util.makeToast
import com.google.android.material.chip.Chip

class SettingsNotificationFragment : BaseBindingFragment<FragmentSettingsNotificationBinding>(
    FragmentSettingsNotificationBinding::inflate
) {

    private val settingsNotificationViewModel: SettingsNotificationViewModel by activityViewModels()
    private val settingsViewModel: SettingsViewModel by activityViewModels()

    private var checkBoxes: MutableList<CheckBox> = mutableListOf()
    private val buyerViews: MutableList<View> = mutableListOf()
    private val sellerViews: MutableList<View> = mutableListOf()
    private var radioButtons: MutableList<RadioButton> = mutableListOf()
    private val viewsToHide: MutableList<View> = mutableListOf()

    private var citiesAdapter: ArrayAdapter<String>? = null

    private lateinit var colorStateListEnabled: ColorStateList
    private lateinit var colorStateListDisabled: ColorStateList

    private var citiesPicked = mutableListOf<String>()

    private var cachedCitiesPicked = mutableListOf<String>()
    private var cachedCheckboxesValues = mutableListOf<Boolean>()
    private var cachedRadioButtonsValues = mutableListOf<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorStateListEnabled = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.grey3), //unchecked color
                ContextCompat.getColor(requireContext(), R.color.orange_light), //checked color
            )
        )

        colorStateListDisabled = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.grey3), //unchecked color
                ContextCompat.getColor(requireContext(), R.color.grey3), //checked color
            )
        )

        binding.apply {
            checkBoxes.addAll(
                listOf(
                    checkboxNotificationApprove,
                    checkboxNotificationNewOrder,
                    checkboxNotificationReject,
                    checkboxNotificationBuyerNewOrder,
                    checkboxNotificationBuyerReject
                )
            )
            buyerViews.addAll(
                listOf(
                    checkboxNotificationApprove,
                    checkboxNotificationNewOrder,
                    checkboxNotificationReject
                )
            )
            sellerViews.addAll(
                listOf(
                    checkboxNotificationBuyerNewOrder,
                    checkboxNotificationBuyerReject,
                    notificationSettingsCitiesInputLayout,
                    notificationSettingsCitiesInput,
                    notificationSettingsNoCities
                )
            )
            radioButtons.addAll(
                listOf(
                    notificationRadioMomentary,
                    notificationRadioPerHour,
                    notificationRadioPer4Hours,
                    notificationRadioPerDay
                )
            )
            viewsToHide.apply {
                addAll(buyerViews)
                addAll(sellerViews)
                addAll(radioButtons)
            }

            changeCheckBoxColorStateList(isEnabled = false)

            notificationSettingsCitiesInput.setOnItemClickListener { parent, view, position, id ->
                notificationSettingsCitiesInput.dismissDropDown()
                TransitionManager.beginDelayedTransition(binding.notificationChipGroup)
                addChipToGroup(notificationSettingsCitiesInput.text.toString())
                makeToast(requireContext(), notificationSettingsCitiesInput.text.toString())
                notificationSettingsCitiesInput.text.clear()
                notificationSettingsCitiesInput.dismissDropDown()
            }

            notificationSettingsCitiesInput.addTextChangedListener {
                if (notificationSettingsCitiesInput.text.toString().isEmpty()) {
                    notificationSettingsCitiesInput.disableDropDown()
                    return@addTextChangedListener
                }

                val substring = binding.notificationSettingsCitiesInput.text.toString()
                settingsNotificationViewModel.updateFilter(
                    input = CitySuggestionsData(
                        substring = substring
                    )
                )
                settingsNotificationViewModel.getCitiesBySubstring()
                notificationSettingsCitiesInput.enableDropDown()
            }
            notificationSettingsCitiesInput.apply {
                threshold = 1
                setDropDownBackgroundResource(R.drawable.background_dialog_white)
            }
        }

        settingsNotificationViewModel.getSuggestedCityLiveData().observe(viewLifecycleOwner) {
            updateAutoCompleteTextView(it.map { city -> city.name })
        }

        setViewsNonEditable()

        settingsViewModel.getEditState().observe(viewLifecycleOwner) {
            when (it) {
                EditState.Editing -> {
                    setViewsEditable()
                }
                EditState.NonEditing -> {
                    setViewsNonEditable()
                }
                EditState.Saved -> {
                    updateCachedCheckBoxes()
                    updateCachedRadioButtons()
                    updateCachedCitiesPicked()
                    //saving data to back-end
                }
                EditState.Canceled -> {
                    restoreCachedCheckBoxes()
                    restoreCachedRadioButtons()
                    restoreCachedCitiesPicked()
                }
            }
            clearSearchText()
        }

        showBuyerViews()
        showSellerViews()

        cachedCheckboxesValues.clear()
        for (i in 0 until checkBoxes.size) {
            cachedCheckboxesValues.add(false)
        }
        cachedRadioButtonsValues.clear()
        for (i in 0 until radioButtons.size) {
            cachedRadioButtonsValues.add(false)
        }

        updateCachedCheckBoxes()
        updateCachedRadioButtons()
        updateCachedCitiesPicked()
    }

    private fun clearSearchText() {
        binding.notificationSettingsCitiesInput.text.clear()
    }

    private fun showBuyerViews() {
        buyerViews.forEach { it.visibility = View.VISIBLE }
        sellerViews.forEach { it.visibility = View.GONE }
    }

    private fun showSellerViews() {
        buyerViews.forEach { it.visibility = View.GONE }
        sellerViews.forEach { it.visibility = View.VISIBLE }
    }

    private fun updateNoCitiesLabel() {
        binding.notificationSettingsNoCities.visibility =
            if (binding.notificationChipGroup.childCount == 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private fun addChipToGroup(city: String) {
        val chip = Chip(requireContext())
        chip.text = city
        chip.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        chip.chipIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_background)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        chip.isClickable = false
        chip.isCheckable = false
        chip.setChipBackgroundColorResource(R.color.gray_chip)
        chip.setCloseIconTintResource(R.color.black)
        binding.notificationChipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener {
            TransitionManager.beginDelayedTransition(binding.notificationChipGroup)
            binding.notificationChipGroup.removeView(chip as View)
            updateNoCitiesLabel()
            citiesPicked.remove(city)
        }
        citiesPicked.add(city)
        updateNoCitiesLabel()
    }

    private fun updateAutoCompleteTextView(cities: List<String>) {
        citiesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
        citiesAdapter?.filter?.filter(binding.notificationSettingsCitiesInput.text.toString())
        binding.notificationSettingsCitiesInput.showDropDown()
        binding.notificationSettingsCitiesInput.apply {
            setAdapter(citiesAdapter)
        }
    }

    private fun setViewsEditable() {
        for (innerView in viewsToHide) {
            innerView.isEnabled = true
        }
        changeCheckBoxColorStateList(isEnabled = true)
        changeChipGroupEnable(isEnabled = true)
    }

    private fun setViewsNonEditable() {
        for (innerView in viewsToHide) {
            innerView.isEnabled = false
        }
        changeCheckBoxColorStateList(isEnabled = false)
        changeChipGroupEnable(isEnabled = false)
    }

    private fun changeCheckBoxColorStateList(isEnabled: Boolean) {
        sellerViews.forEach {
            if (it is CheckBox) it.buttonTintList =
                if (isEnabled) colorStateListEnabled else colorStateListDisabled
        }
        buyerViews.forEach {
            if (it is CheckBox) it.buttonTintList =
                if (isEnabled) colorStateListEnabled else colorStateListDisabled
        }
    }

    private fun changeChipGroupEnable(isEnabled: Boolean) {
        val chipGroup = binding.notificationChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i)
            if (chip is Chip) {
                chip.isCloseIconVisible = isEnabled
            }
        }
    }

    private fun restoreCachedCheckBoxes() {
        var index = 0
        for (checkBox in checkBoxes) {
            checkBox.isChecked = cachedCheckboxesValues[index++]
        }
    }

    private fun restoreCachedRadioButtons() {
        var index = 0
        for (radioButton in radioButtons) {
            radioButton.isChecked = cachedRadioButtonsValues[index++]
        }
    }

    private fun restoreCachedCitiesPicked() {
        citiesPicked = cachedCitiesPicked
        TransitionManager.beginDelayedTransition(binding.notificationChipGroup)
        binding.notificationChipGroup.removeAllViews()
        for (city in citiesPicked) {
            addChipToGroup(city)
        }
    }

    private fun updateCachedCheckBoxes() {
        var index = 0
        for (checkBox in checkBoxes) {
            cachedCheckboxesValues[index++] = checkBox.isChecked
        }
    }

    private fun updateCachedRadioButtons() {
        var index = 0
        for (radioButton in radioButtons) {
            cachedRadioButtonsValues[index++] = radioButton.isChecked
        }
    }

    private fun updateCachedCitiesPicked() {
        cachedCitiesPicked = citiesPicked.toMutableList()
    }
}