package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice

class AppAccessorFactory(textToVoice: TextToVoice) {
    private var accessorHome: AppAccessorHome = AppAccessorHome(textToVoice)
    private var accessorDefault: AppAccessorDefault = AppAccessorDefault(textToVoice)
    private var accessorWhatsapp: AppAccessorWhatsapp = AppAccessorWhatsapp(textToVoice)

    fun getAccessor(event: AccessibilityEvent): AppAccessor {
        if (event.packageName != null) {
            val packageName = event.packageName.toString()
            return when {
                packageName == this.accessorWhatsapp.packageName -> this.accessorWhatsapp
                packageName.contains("launcher") -> this.accessorHome
                else -> this.accessorDefault
            }
        }

        return this.accessorDefault
    }
}