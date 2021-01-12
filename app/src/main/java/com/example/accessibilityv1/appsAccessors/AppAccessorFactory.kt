package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice

class AppAccessorFactory(textToVoice: TextToVoice) {
    private var accessorHome: AppAccessorHome = AppAccessorHome(textToVoice)
    private var accessorOnce: AppAccessorOnce = AppAccessorOnce(textToVoice)
    private var accessorSettings: AppAccessorSettings = AppAccessorSettings(textToVoice)
    private var accessorWhatsapp: AppAccessorWhatsapp = AppAccessorWhatsapp(textToVoice)

    fun getAccessor(event: AccessibilityEvent): AppAccessor {
        val packageName = event.packageName?.toString()
        return when {
            this.accessorOnce.packageName == packageName -> this.accessorOnce
            this.accessorSettings.packageName == packageName -> this.accessorSettings
            this.accessorWhatsapp.packageName == packageName -> this.accessorWhatsapp
            else -> this.accessorHome
        }
    }
}