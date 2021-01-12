package com.example.accessibilityv1.appsAccessors

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice

class AppAccessorOnce(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val packageName: String get() = "es.once.gold"
    override val appIconLabel: String get() = "Gestor ONCE de libros digitales"

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_FOCUSED == event.eventType) {
            this.speak("ONCE " + event.text.toString().split(",")[0])
        }
    }
}