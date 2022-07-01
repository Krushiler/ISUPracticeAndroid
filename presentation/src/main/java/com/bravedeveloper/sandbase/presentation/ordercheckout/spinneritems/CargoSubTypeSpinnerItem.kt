package com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems

import com.bravedeveloper.sandbase.presentation.base.defaultspinner.DefaultSpinnerItem

data class CargoSubTypeSpinnerItem(
    override val itemName: String,
    override val id: String
) : CargoItem()