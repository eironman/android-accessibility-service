package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.home.AppScreenAcessorHome
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp.AppScreenAccessorWhatsappChats

class AppAccessorHome(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = ""
    override val appIconLabel: String get() = ""
    override var appScreenAccessor: AppScreenAccessor = AppScreenAcessorHome(textToVoice)

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.appScreenAccessor.onAccessibilityEvent(event)
    }
}