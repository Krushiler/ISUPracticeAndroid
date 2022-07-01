package com.bravedeveloper.sandbase.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseAndroidViewModel(application: Application): AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun Disposable.untilCleared(): Disposable {
        addDisposable(this)
        return this
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}