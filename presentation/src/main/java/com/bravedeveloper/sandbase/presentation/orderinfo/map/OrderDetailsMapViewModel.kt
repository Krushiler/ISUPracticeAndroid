package com.bravedeveloper.sandbase.presentation.orderinfo.map

import android.content.res.Resources
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.presentation.orderinfo.stub.OrderStub
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OrderDetailsMapViewModel @Inject constructor(
    private val geocoder: Geocoder,
    private val resources: Resources
) : ViewModel() {

    private var order: OrderStub = OrderStub()

    val latLngLiveData: MutableLiveData<LatLng> by lazy {
        MutableLiveData<LatLng>()
    }

    val messageLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val orderLiveData: MutableLiveData<OrderStub> by lazy {
        MutableLiveData<OrderStub>(OrderStub())
    }

    init {
        orderLiveData.value = order
    }

    fun getLatitudeLongitude() {
        try {
            val address = geocoder.getFromLocationName(order.address, 1)[0]
            latLngLiveData.value = LatLng(address.latitude, address.longitude)
        } catch (e: IllegalArgumentException) {
            messageLiveData.value = resources.getString(R.string.error_find_location)
        } catch (e: IOException) {
            messageLiveData.value = resources.getString(R.string.error_conection)
        }
    }
}