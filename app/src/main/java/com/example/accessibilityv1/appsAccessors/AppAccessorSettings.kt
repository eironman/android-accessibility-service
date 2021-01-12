package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice

class AppAccessorSettings(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = "com.android.settings"
    override val appIconLabel: String get() = "settings"

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_FOCUSED == event.eventType) {
            this.speak(event.text.toString().split(",")[0])
        }
    }
}