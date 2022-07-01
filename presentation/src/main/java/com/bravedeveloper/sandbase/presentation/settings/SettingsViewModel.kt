package com.bravedeveloper.sandbase.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.bravedeveloper.sandbase.presentation.settings.general.EditState
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SettingsViewModel @javax.inject.Inject constructor(
    private val router: Router
) : ViewModel() {

    private val editState = MutableLiveData<EditState>()

    init {
        editState.value = EditState.NonEditing
    }

    fun getEditState(): LiveData<EditState> = editState

    fun setEditable() {
        editState.value = EditState.Editing
    }

    fun setNonEditable() {
        editState.value = EditState.NonEditing
    }

    fun setCanceled() {
        editState.value = EditState.Canceled
    }

    fun setSaved() {
        editState.value = EditState.Saved
    }

    fun goToAuthorizationScreen() {
        router.navigateTo(Screens.authorizationScreen())
    }
}