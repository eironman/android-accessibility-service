package com.example.accessibilityv1.appsAccessors

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppAccessorSettings(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = "com.android.settings"
    override val appIconLabel: String get() = "settings"
    override var appScreenAccessor: AppScreenAccessor
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

    }
}