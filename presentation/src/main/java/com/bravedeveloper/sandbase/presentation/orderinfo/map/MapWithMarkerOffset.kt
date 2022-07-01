package com.bravedeveloper.sandbase.presentation.orderinfo.map

import android.content.Context
import android.util.AttributeSet
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val ZOOM = 12f
const val NO_OFFSET = 0f

class MapWithMarkerOffset(context: Context, attributeSet: AttributeSet) :
    MapView(context, attributeSet) {

    var markerYBottomOffset: Int = 0
    var googleMap: GoogleMap? = null

    fun setMarkerBottomOffset(markerBottomPadding: Int) {
        //get left top coordinate of map view
        val mapLocation = IntArray(2)
        getLocationOnScreen(mapLocation)
        val mapYCoordinate = mapLocation[1]

        markerYBottomOffset = (height - markerBottomPadding + mapYCoordinate) / 2
    }

    fun showMarker(latLng: LatLng) {
        googleMap?.apply {
            addMarker(MarkerOptions().position(latLng))
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM))
            moveCamera(CameraUpdateFactory.scrollBy(NO_OFFSET, markerYBottomOffset.toFloat()))
        }
    }
}