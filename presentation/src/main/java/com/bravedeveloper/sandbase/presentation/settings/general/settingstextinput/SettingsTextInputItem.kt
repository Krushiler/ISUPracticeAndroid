package com.bravedeveloper.sandbase.presentation.settings.general.settingstextinput

import com.bravedeveloper.sandbase.presentation.settings.general.SettingsItem

data class SettingsTextInputItem(
    override val itemID: Int,
    override val title: String,
    var text: String,
    override var isEnabled: Boolean = false
): SettingsItem()
