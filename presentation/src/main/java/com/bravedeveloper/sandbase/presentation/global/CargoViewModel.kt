package com.bravedeveloper.sandbase.presentation.global

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.city.cargo.Cargo
import com.bravedeveloper.domain.model.city.cargo.Measure
import com.bravedeveloper.domain.usecase.orders.GetCargoSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CargoViewModel @Inject constructor(private val getCargoSingleUseCase: GetCargoSingleUseCase) : BaseViewModel() {

    private val cargoLiveData = MutableLiveData<List<Cargo>>()
    private val measuresLiveData = MutableLiveData<List<Measure>>()

    fun getCargo() {
        getCargoSingleUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cargoLiveData.value = it.data?.cargo?.cargo
                measuresLiveData.value = it.data?.cargo?.measures
            },{
                Log.e(it::class.simpleName, it.message.toString())
            }).untilCleared()
    }

    fun getCargoLiveData(): LiveData<List<Cargo>> = cargoLiveData

    fun getMeasuresLiveData(): LiveData<List<Measure>> = measuresLiveData

}