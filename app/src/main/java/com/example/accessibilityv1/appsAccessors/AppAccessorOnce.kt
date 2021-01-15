package com.example.accessibilityv1.appsAccessors

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppAccessorOnce(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = "es.once.gold"
    override val appIconLabel: String get() = "Gestor ONCE de libros digitales"
    override var appScreenAccessor: AppScreenAccessor
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

    }
}