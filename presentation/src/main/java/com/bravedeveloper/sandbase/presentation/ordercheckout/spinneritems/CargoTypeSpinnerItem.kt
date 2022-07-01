package com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems

import com.bravedeveloper.sandbase.presentation.base.defaultspinner.DefaultSpinnerItem

data class CargoTypeSpinnerItem(
    override val itemName: String,
    override val id: String,
    val numberPosition: Int,
    val subtypes: List<CargoSubTypeSpinnerItem>
) : CargoItem()