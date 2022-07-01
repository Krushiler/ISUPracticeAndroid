package com.bravedeveloper.sandbase.presentation.ordercheckout

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.bravedeveloper.domain.model.city.cargo.Measure
import com.bravedeveloper.domain.model.city.hours.ReceivingHoursEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentMapCheckoutOfferBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.global.CargoViewModel
import com.bravedeveloper.sandbase.presentation.ordercheckout.addressrecycler.AddressSearchAdapter
import com.bravedeveloper.sandbase.presentation.ordercheckout.addressrecycler.AddressSearchItem
import com.bravedeveloper.sandbase.presentation.ordercheckout.addressrecycler.OnAddressItemClick
import com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems.*
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.text.SimpleDateFormat
import java.util.*


private const val ZOOM = 16F
private const val INTERVAL = 60000L
private const val FASTEST_INTERVAL = 5000L
private const val TEXT_INPUT_ELEVATION = 4F

@AndroidEntryPoint
class OrderCheckoutMapFragment :
    BaseBindingFragment<FragmentMapCheckoutOfferBinding>(FragmentMapCheckoutOfferBinding::inflate),
    OnMapReadyCallback, OnAddressItemClick {

    private val LOCATION_PERMISSION_CODE = 1
    private val arrayOfDisplayedValues = arrayOf("c 8:00 до 14:00", "с 14:00 до 20:00")
    private val orderCheckoutMapViewModel: OrderCheckoutMapViewModel by viewModels()
    private val cargoViewModel: CargoViewModel by viewModels()
    private val orderInfoViewModel: OrderInfoViewModel by activityViewModels()

    private lateinit var addressesAdapter: CompositeDelegateAdapter

    private val measures = mutableListOf<Measure>()
    private var pickedMeasure = Measure("", "")

    private val bottomSheetAddressEditViews = mutableListOf<View>()
    private val bottomSheetNoAddressViews = mutableListOf<View>()
    private var pickedCoords: Pair<Double, Double>? = null
    private var pickedDate = Date()

    private var receivingHours = ReceivingHoursEnum.MORNING

    private var isAddressEditTextActive = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderCheckoutMapViewModel.getCreatedOrderNumberLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                orderInfoViewModel.getOrder(it)
                orderCheckoutMapViewModel.goToOrderInfoScreen()
            }
        }

        initMap()
        initViews()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(binding.checkoutMapView) {
            this.googleMap = googleMap
            googleMap.setOnMapLongClickListener { latLng ->
                addPlaceMarker(latLng)
                orderCheckoutMapViewModel.getAddressByCoordinates(
                    latLng.latitude,
                    latLng.longitude
                )
            }
            moveToCurrentLocation(googleMap)
            orderCheckoutMapViewModel.getCoordinatesByAddressLiveData()
                .observe(viewLifecycleOwner) {
                    addPlaceMarker(LatLng(it.geo_lat, it.geo_lon))
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.geo_lat,
                                it.geo_lon
                            ), ZOOM
                        )
                    )
                }

            googleMap.uiSettings.isCompassEnabled = false

            binding.checkoutGeoFab.setOnClickListener {
                if (pickedCoords != null) {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                pickedCoords!!.first,
                                pickedCoords!!.second
                            ), ZOOM
                        )
                    )
                }
            }

        }
    }

    private fun moveToCurrentLocation(googleMap: GoogleMap) {
        checkLocationPermission()
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL
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
                                ), ZOOM
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

    private fun initMap() {
        binding.checkoutMapView.apply {
            onCreate(Bundle())
            onResume()
            getMapAsync(this@OrderCheckoutMapFragment)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initViews() {
        binding.apply {

            checkoutSizeEditText.setOnFocusChangeListener { v, hasFocus ->
                v.elevation = if (hasFocus) {
                    TEXT_INPUT_ELEVATION
                } else {
                    0f
                }
            }

            orderCheckoutMapViewModel.getAddressByCoordinatesLiveData()
                .observe(viewLifecycleOwner) {
                    checkoutAdressEditText.setText(it.value)
                }

            val sizeSpinnerAdapter =
                MeasureSpinnerAdapter(context = requireContext(), measureArray = measures)

            cargoViewModel.getMeasuresLiveData().observe(viewLifecycleOwner) {
                measures.clear()
                measures.addAll(it)

                sizeSpinnerAdapter.notifyDataSetChanged()
                checkoutSizeTypeSpinner.setSelection(1)
            }

            checkoutSizeTypeSpinner.adapter = sizeSpinnerAdapter
            checkoutSizeTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    if (position != 0) pickedMeasure = measures[position-1]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            checkoutButton.setOnClickListener {
                if (pickedCoords != null) {
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    format.timeZone = TimeZone.getTimeZone("UTC")

                    var subtypeString = ""
                    if (checkoutUndertypeSpinner.selectedItem != null && checkoutUndertypeSpinner.selectedItem is CargoSubTypeSpinnerItem) {
                        subtypeString =
                            (checkoutUndertypeSpinner.selectedItem as CargoSubTypeSpinnerItem).itemName
                    }

                    orderCheckoutMapViewModel.orderCreate(
                        coords = pickedCoords!!,
                        destination = checkoutAdressEditText.text.toString(),
                        cargoType = (checkoutMaterialSpinner.selectedItem as CargoTypeSpinnerItem).itemName,
                        cargoSubType = subtypeString,
                        cargoVolume = checkoutSizeEditText.text.toString(),
                        date = format.format(pickedDate),
                        time = receivingHours,
                        comment = checkoutCommentaryEditText.text.toString(),
                        measure = pickedMeasure.name
                    )
                }
            }

        }

        setupDateTextView()
        setupTimeTextView()
        setupCommentaryEditText()
        setupSpinners()
        setupBottomSheet()
    }

    private fun setupDateTextView() {
        binding.apply {
            checkoutDatePicker.visibility = View.GONE

            val format = SimpleDateFormat("dd.MM.yy")
            val calendar = Calendar.getInstance()

            pickedDate = calendar.time

            checkoutDateTextView.text = format.format(calendar.time)

            checkoutDateTextView.setOnClickListener {
                it.isSelected = !it.isSelected
                TransitionManager.beginDelayedTransition(checkoutBottomSheet)
                checkoutDatePicker.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }

            checkoutDatePicker.setOnDateChangeListener { calendarView, year, month, day ->
                calendar.set(year, month, day)
                pickedDate = calendar.time
                checkoutDateTextView.text = format.format(calendar.time)
            }
        }
    }

    private fun setupTimeTextView() {
        binding.apply {
            checkoutTimePicker.displayedValues = arrayOfDisplayedValues
            checkoutTimePicker.setOnScrollListener { _, _ ->
                checkoutTimeTextView.text =
                    checkoutTimePicker.displayedValues[checkoutTimePicker.value]
                receivingHours = ReceivingHoursEnum.values()[checkoutTimePicker.value]
            }
            checkoutTimeTextView.setOnClickListener {
                it.isSelected = !it.isSelected
                TransitionManager.beginDelayedTransition(checkoutBottomSheet)
                checkoutTimePicker.visibility = if (it.isSelected) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setupCommentaryEditText() {
        binding.apply {
            checkoutCommentaryEditText.imeOptions = EditorInfo.IME_ACTION_DONE
            checkoutCommentaryEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            checkoutCommentaryEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    binding.checkoutLettersLeft.text =
                        "${120 - p0.toString().length} ${resources.getString(R.string.letters_left)}"
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun setupSpinners() {
        binding.apply {
            cargoViewModel.getCargo()

            val materials = mutableListOf<CargoTypeSpinnerItem>()
            val subMaterials = mutableListOf<CargoSubTypeSpinnerItem>()

            val materialAdapter = CargoTypeSpinnerAdapter(requireContext(), materials)
            val subMaterialsAdapter = CargoSubTypeSpinnerAdapter(requireContext(), subMaterials)

            cargoViewModel.getCargoLiveData().observe(viewLifecycleOwner) {
                materials.clear()
                materials.addAll(it.map { cargoType ->
                    CargoTypeSpinnerItem(
                        cargoType.type,
                        cargoType.id,
                        cargoType.positionNumber,
                        cargoType.subtypes.map { cargoSubType ->
                            CargoSubTypeSpinnerItem(cargoSubType.subtype, cargoSubType.id)
                        })
                })
                materials.sortBy { item -> item.numberPosition }
                materialAdapter.notifyDataSetChanged()
            }

            checkoutMaterialSpinner.adapter = materialAdapter
            checkoutMaterialSpinner.isEnabled = true
            checkoutUndertypeSpinner.adapter = subMaterialsAdapter
            checkoutUndertypeSpinner.isEnabled = false
            checkoutMaterialSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        p3: Long
                    ) {
                        val currentMaterial = materialAdapter.getItem(position)

                        if (currentMaterial != null && currentMaterial.subtypes.isNotEmpty()) {

                            subMaterials.clear()
                            subMaterials.addAll(currentMaterial.subtypes)
                            subMaterialsAdapter.notifyDataSetChanged()

                            checkoutUndertypeSpinner.isEnabled = true
                            val child = checkoutUndertypeSpinner.getChildAt(0)
                            if (child is ViewGroup) {
                                for (i in 0 until child.childCount) {
                                    child.getChildAt(i).isEnabled = true
                                }
                            }
                        } else {
                            checkoutUndertypeSpinner.isEnabled = false
                            val child = checkoutUndertypeSpinner.getChildAt(0)
                            if (child is ViewGroup) {
                                for (i in 0 until child.childCount) {
                                    child.getChildAt(i).isEnabled = false
                                }
                            }
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
    }

    private fun setupBottomSheet() {
        binding.apply {
            val bottomSheetBehaviour = BottomSheetBehavior.from(checkoutBottomSheet)

            bottomSheetNoAddressViews.addAll(
                listOf(
                    checkoutUndertypeSpinner,
                    checkoutMaterialSpinner,
                    checkoutSizeLayout,
                    checkoutDateTextView,
                    checkoutTimeTextView,
                    checkoutDateTextView,
                    checkoutButton,
                    checkoutCommentaryEditText,
                    checkoutLettersLeft
                )
            )
            bottomSheetAddressEditViews.add(checkoutAddressListView)

            checkoutAdressEditText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    openSearch()
                } else {
                    closeSearch()
                }
            }

            KeyboardVisibilityEvent.setEventListener(requireActivity()) {
                if (!it && checkoutAdressEditText.isFocused) {
                    closeSearch()
                    checkoutAdressEditText.clearFocus()
                }
            }

            addressesAdapter =
                CompositeDelegateAdapter(AddressSearchAdapter(this@OrderCheckoutMapFragment))
            checkoutAddressListView.adapter = addressesAdapter

            checkoutAdressEditText.addTextChangedListener {
                if (checkoutAdressEditText.isFocused) {
                    orderCheckoutMapViewModel.getSuggestion(checkoutAdressEditText.text.toString())
                }

                TransitionManager.beginDelayedTransition(checkoutBottomSheet)
            }

            orderCheckoutMapViewModel.getSuggestionsLiveData().observe(viewLifecycleOwner) {
                val addresses = mutableListOf<AddressSearchItem>()
                it.forEach { suggestion ->
                    addresses.add(separateAddress(suggestion.value))
                }
                addressesAdapter.swapData(addresses)
            }

            checkoutBackFab.setOnClickListener { requireActivity().onBackPressed() }

            bottomSheetBehaviour.isFitToContents = true
            bottomSheetBehaviour.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        checkoutBackFab.isClickable = false
                        checkoutGeoFab.isClickable = false
                    } else {
                        checkoutBackFab.isClickable = true
                        checkoutGeoFab.isClickable = true
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > bottomSheetBehaviour.halfExpandedRatio) {
                        val newAlpha =
                            -(-1 / (bottomSheetBehaviour.halfExpandedRatio - 1)) * slideOffset - (1 / (bottomSheetBehaviour.halfExpandedRatio - 1))
                        checkoutGeoFab.animate().alpha(newAlpha).setDuration(0).start()
                        checkoutBackFab.animate().alpha(newAlpha).setDuration(0).start()
                    } else {
                        checkoutGeoFab.animate().alpha(1f).setDuration(0).start()
                        checkoutBackFab.animate().alpha(1f).setDuration(0).start()
                    }
                }
            })
        }
    }

    private fun addPlaceMarker(position: LatLng) {
        pickedCoords = Pair(position.latitude, position.longitude)
        binding.checkoutMapView.googleMap?.clear()
        binding.checkoutMapView.googleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        )
    }

    private fun openSearch() {
        Toast.makeText(requireContext(), "Open", Toast.LENGTH_SHORT).show()
        binding.apply {
            bottomSheetNoAddressViews.forEach {
                it.visibility = View.GONE
            }
            bottomSheetAddressEditViews.forEach {
                it.visibility = View.VISIBLE
            }
            BottomSheetBehavior.from(checkoutBottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(checkoutBottomSheet).isDraggable = false
            BottomSheetBehavior.from(checkoutBottomSheet).isFitToContents = false
            TransitionManager.beginDelayedTransition(checkoutBottomSheet)
        }
    }

    private fun closeSearch() {
        binding.apply {
            BottomSheetBehavior.from(checkoutBottomSheet).isFitToContents = true
            bottomSheetAddressEditViews.forEach {
                it.visibility = View.GONE
            }
            bottomSheetNoAddressViews.forEach {
                it.visibility = View.VISIBLE
            }
            checkoutDatePicker.visibility = View.GONE
            checkoutTimePicker.visibility = View.GONE
            BottomSheetBehavior.from(checkoutBottomSheet).isDraggable = true
            TransitionManager.beginDelayedTransition(checkoutBottomSheet)
        }
    }

    private fun separateAddress(address: String): AddressSearchItem {
        val hint = address.substringBeforeLast(',')
        val result = if (address.length - hint.length != 0) {
            val temp = address.substring(hint.length)
            if (temp[0] == ',' || temp[0] == ' ') {
                address.substring(hint.length).removeRange(0, 1)
            } else {
                address.substring(hint.length)
            }
        } else {
            ""
        }

        return AddressSearchItem(
            hint = hint,
            result = result,
            fullAddress = address
        )
    }

    override fun onClick(item: AddressSearchItem) {
        val address = item.fullAddress
        binding.checkoutAdressEditText.setText(address)
        orderCheckoutMapViewModel.getCoordinatesByAddress(address = address)
        requireActivity().currentFocus?.let { view ->
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding.checkoutAdressEditText.clearFocus()
    }
}
