package com.example.accessibilityv1.appsAccessors

import android.accessibilityservice.GestureDescription
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

abstract class AppAccessor constructor(var textToVoice: TextToVoice) {
    abstract val packageName: String
    abstract val appIconLabel: String
    abstract var appScreenAccessor: AppScreenAccessor

    abstract fun onAccessibilityEvent(event: AccessibilityEvent)

    fun onKeyEvent(keyEvent: KeyEvent,
                   doGlobalAction: (action: Int) -> Unit,
                   doGesture: (gesture: GestureDescription) -> Unit): Boolean {
        return this.appScreenAccessor.onKeyEvent(keyEvent, doGlobalAction, doGesture)
    }
}