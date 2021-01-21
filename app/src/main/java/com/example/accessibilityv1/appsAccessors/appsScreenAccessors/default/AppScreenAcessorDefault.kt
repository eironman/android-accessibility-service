package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.default

import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAcessorDefault(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.speakOnFocus(event)
    }
}