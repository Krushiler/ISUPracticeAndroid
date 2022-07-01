package com.bravedeveloper.sandbase.presentation.orderinfo.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentOrderDetailsMapBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderinfo.stub.OrderStub
import com.google.android.gms.maps.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsMapFragment :
    BaseBindingFragment<FragmentOrderDetailsMapBinding>(FragmentOrderDetailsMapBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: OrderDetailsMapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initMap()

        observeToOrderLiveData()
        observeToSnackbarMessage()
    }

    private fun initToolbar() {
        binding.mapFragmentToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.icon_back)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun initMap() {
        binding.mapFragmentMapView.apply {
            onCreate(Bundle())
            onResume()
            getMapAsync(this@OrderDetailsMapFragment)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(binding.mapFragmentMapView) {
            this.googleMap = googleMap

            val infoLayoutTopCoordinate = getViewTopCoordinate(binding.mapFragmentOrderInfoLayout)
            setMarkerBottomOffset(infoLayoutTopCoordinate)
        }

        observeToChangeLatLng()
        viewModel.getLatitudeLongitude()
    }

    private fun observeToOrderLiveData() {
        viewModel.orderLiveData.observe(viewLifecycleOwner, { Order ->
            renderOrder(Order)
        })
    }

    private fun observeToChangeLatLng() {
        viewModel.latLngLiveData.observe(viewLifecycleOwner, { latLng ->
            binding.mapFragmentMapView.showMarker(latLng)
        })
    }

    private fun observeToSnackbarMessage() {
        viewModel.messageLiveData.observe(viewLifecycleOwner, { message ->
            showSnackbar(message)
        })
    }

    private fun renderOrder(order: OrderStub) {
        with(binding) {
            mapFragmentTitle.text = order.title
            mapFragmentLocation.text = order.address
            mapFragmentDateTime.text = (order.date + ", " + order.time)
            mapFragmentComment.text = order.comment
        }
    }

    private fun getViewTopCoordinate(view: View): Int {
        val infoLayoutLocation = IntArray(2)
        view.getLocationOnScreen(infoLayoutLocation)
        return infoLayoutLocation[1]
    }

    private fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}