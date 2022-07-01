package com.bravedeveloper.domain.model.city.order

import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.user.usertypes.AdminType
import com.squareup.moshi.Json

data class Region (

    @Json(name="id") var id : Int,
    @Json(name="name") var name : String,
    @Json(name="cities") var cities : Array<City>,
    @Json(name="users") var users : Array<AdminType>,
    @Json(name="orders") var orders : Array<Order>

)
