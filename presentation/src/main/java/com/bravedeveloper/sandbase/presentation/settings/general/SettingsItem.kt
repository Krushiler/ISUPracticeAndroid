package com.bravedeveloper.sandbase.presentation.settings.general

abstract class SettingsItem {
    abstract val itemID: Int
    abstract val title: String
    abstract var isEnabled: Boolean
}