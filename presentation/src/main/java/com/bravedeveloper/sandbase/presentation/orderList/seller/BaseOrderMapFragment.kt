package com.bravedeveloper.sandbase.presentation.orderList.seller

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.sandbase.databinding.FragmentOrderMapBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.orderList.BaseOrderListSearchFilter
import com.bravedeveloper.sandbase.presentation.orderList.OrdersViewModel
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.bravedeveloper.sandbase.presentation.sellerfilter.SellerFilterViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

abstract class BaseOrderMapFragment<V : ViewBinding>(private val bottomSheetBinder: (LayoutInflater, ViewGroup?, Boolean) -> V) :
    BaseBindingFragment<FragmentOrderMapBinding>(FragmentOrderMapBinding::inflate),
    OnMapReadyCallback {

    private val LOCATION_PERMISSION_CODE = 1

    protected val ordersViewModel: OrdersViewModel by activityViewModels()
    protected open val orderMapViewModel: OrderMapViewModel by viewModels()
    protected val sellerFilterViewModel: SellerFilterViewModel by activityViewModels()
    protected val orderInfoViewModel: OrderInfoViewModel by activityViewModels()

    protected abstract val defaultFilterSettings: BaseOrderListSearchFilter
    private val ordersMarkersMap = mutableMapOf<Marker, Order>()

    private var ordersCount = 0
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<FrameLayout>
    private var bottomSheetContentBinding: V? = null

    protected val bottomSheetBinding: V
        get() = requireNotNull(bottomSheetContentBinding) {
            "Binding is only valid between onCreateView and onDestroyView"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = super.onCreateView(inflater, container, savedInstanceState)

        bottomSheetContentBinding = bottomSheetBinder.invoke(inflater, container, false)

        return fragmentView
    }

    override fun onResume() {
        super.onResume()
        ordersViewModel.setCountOrders(ordersCount)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellerFilterViewModel.getFilterLiveData().observe(viewLifecycleOwner) {
            val filter = it.copy()
            filter.sort = defaultFilterSettings.sort
            filter.role = defaultFilterSettings.role
            filter.searchType = defaultFilterSettings.searchType
            filter.needOwnUserId = defaultFilterSettings.needOwnUserId
            orderMapViewModel.getOrders(filter)
        }

        orderMapViewModel.getOrdersCountLiveData().observe(viewLifecycleOwner) {
            ordersCount = it
            if (isResumed) ordersViewModel.setCountOrders(ordersCount)
        }

        orderMapViewModel.getOrdersLiveData().observe(viewLifecycleOwner) {
            addOrdersToMap(it)
        }

        initMap()
        initBottomSheetOrder()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        bottomSheetBehaviour = BottomSheetBehavior.from(binding.orderBottomSheet)
        bottomSheetBehaviour.isDraggable = false
        bottomSheetBehaviour.isHideable = true
        closeBottomSheet()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(binding.mapOffset) {
            this.googleMap = googleMap
            moveToCurrentLocation(googleMap)
            googleMap.uiSettings.isCompassEnabled = false
            googleMap.setOnMarkerClickListener {
                ordersMarkersMap[it]?.let { marker ->
                    setOrderDataToBottomSheet(marker)
                    openBottomSheet()
                }
                true
            }
        }
    }

    private fun initMap() {
        binding.mapOffset.apply {
            onCreate(Bundle())
            onResume()
            getMapAsync(this@BaseOrderMapFragment)
        }
    }

    private fun addOrdersToMap(orders: List<Order>) {
        binding.mapOffset.googleMap?.clear()
        ordersMarkersMap.clear()
        orders.forEach { order ->
            val orderLat = order.coords?.get(0)
            val orderLong = order.coords?.get(1)
            if (orderLat != null && orderLong != null) {
                val marker = addMarker(LatLng(orderLat, orderLong))
                if (marker != null) {
                    ordersMarkersMap[marker] = order
                }
            }
        }
    }

    private fun initBottomSheetOrder() {
        val view = bottomSheetContentBinding?.root
        binding.orderBottomSheet.addView(view)
    }

    protected abstract fun setOrderDataToBottomSheet(order: Order)

    protected fun openBottomSheet() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    protected fun closeBottomSheet() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
    }

    protected fun addMarker(position: LatLng): Marker? {
        return binding.mapOffset.googleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        )
    }

    protected fun goToOrder(number: Int) {
        orderInfoViewModel.getOrder(number)
        orderMapViewModel.goToOrderInfoScreen()
    }

    private fun moveToCurrentLocation(googleMap: GoogleMap) {
        checkLocationPermission()
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), 16f
                            )
                        )
                    }
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                requireActivity().mainLooper
            )
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_CODE
        )
    }

}