package com.bravedeveloper.sandbase.presentation.orderList.buyer.evaluating

import android.util.Log
import com.bravedeveloper.domain.model.user.rating.RateInput
import com.bravedeveloper.domain.usecase.RateUserSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderListEvaluatingViewModel @Inject constructor(
    private val rateUserSingleUseCase: RateUserSingleUseCase
) : BaseViewModel() {

    fun rateUser(input: RateInput) {
        rateUserSingleUseCase.saveInput(input = input)

        rateUserSingleUseCase.execute()
    }
}
