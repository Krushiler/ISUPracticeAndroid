package com.bravedeveloper.sandbase.presentation.ordercheckout.addressrecycler

import com.bravedeveloper.sandbase.databinding.ItemSearchAddressBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class AddressSearchAdapter(private val onItemClick: OnAddressItemClick) :
    ViewBindingDelegateAdapter<AddressSearchItem, ItemSearchAddressBinding>(
        ItemSearchAddressBinding::inflate
    ) {

    override fun isForViewType(item: Any) = item is AddressSearchItem

    override fun AddressSearchItem.getItemId() = hint

    override fun ItemSearchAddressBinding.onBind(item: AddressSearchItem) {
        tvAddressSearchHint.text = item.hint
        tvAddressSearchResult.text = item.result

        tvAddressSearchResult.setOnClickListener {
            onItemClick.onClick(item)
        }
        tvAddressSearchHint.setOnClickListener {
            onItemClick.onClick(item)
        }
    }
}