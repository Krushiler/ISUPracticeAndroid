package com.bravedeveloper.sandbase.presentation.settings.general.settingstextinput

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.bravedeveloper.sandbase.databinding.SettingsBlockItemBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class SettingsTextInputAdapter(
    val doOnTextChanged: (item: SettingsTextInputItem, text: String) -> Unit
):
    ViewBindingDelegateAdapter<SettingsTextInputItem, SettingsBlockItemBinding>(
        SettingsBlockItemBinding::inflate
    ) {

    private val textChangedListeners = mutableMapOf<EditText, TextWatcher>()

    override fun isForViewType(item: Any) = item is SettingsTextInputItem

    override fun SettingsTextInputItem.getItemId() = itemID

    override fun SettingsBlockItemBinding.onBind(item: SettingsTextInputItem) {
        inputLayout.hint = item.title

        inputEditText.setText(item.text)

        inputEditText.removeTextChangedListener(textChangedListeners[inputEditText])
        textChangedListeners[inputEditText] = inputEditText.addTextChangedListener {
            if (inputEditText.isFocused) {
                doOnTextChanged(item, it.toString())
            }
        }

        if (item.isEnabled) {
            inputEditText.isEnabled = true
            inputLayout.isEnabled = true
        } else {
            inputEditText.isEnabled = false
            inputLayout.isEnabled = false
        }
    }
}