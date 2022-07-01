package com.bravedeveloper.sandbase.presentation.base.util

import android.content.res.Resources
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.sandbase.R
import java.util.*

fun Order.getTitle(resources: Resources) : String{
    var subtype = ""
    if (cargoSubtype != null) {
        subtype = " $cargoSubtype"
    }

    return "$cargoType$subtype, $cargoVolume ${
        localizeMeasure(resources).lowercase(
            Locale.getDefault()
        )
    }"
}

fun Order.getVolumeAndMeasure(resources: Resources): String {
    return "$cargoVolume ${localizeMeasure(resources)}"
}

fun Order.localizeMeasure(resources: Resources): String {
    var newMeasure = measure

    if (measure?.lowercase(Locale.getDefault()) == resources.getString(R.string.tonns_switch).lowercase(Locale.getDefault())) {
        newMeasure = cargoVolume?.toInt()?.let { resources.getQuantityString(R.plurals.tonns, it) }
    }

    return newMeasure.orEmpty()
}