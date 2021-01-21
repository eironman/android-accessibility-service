package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.default.AppScreenAcessorDefault

class AppAccessorDefault(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = ""
    override val appIconLabel: String get() = ""
    override var appScreenAccessor: AppScreenAccessor = AppScreenAcessorDefault(textToVoice)

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.appScreenAccessor.onAccessibilityEvent(event)
    }
}
