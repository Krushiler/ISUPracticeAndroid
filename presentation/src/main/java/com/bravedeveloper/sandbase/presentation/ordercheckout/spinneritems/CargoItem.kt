package com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems

import com.bravedeveloper.sandbase.presentation.base.defaultspinner.DefaultSpinnerItem

abstract class CargoItem() : DefaultSpinnerItem() {
    abstract val id: String
}