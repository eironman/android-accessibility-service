package com.example.accessibilityv1.appsAccessors

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.KeyAction
import com.example.accessibilityv1.UserInput
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

abstract class AppAccessor constructor(var textToVoice: TextToVoice) {
    abstract val packageName: String
    abstract val appIconLabel: String
    abstract var appScreenAccessor: AppScreenAccessor

    abstract fun onAccessibilityEvent(event: AccessibilityEvent)

    fun onKeyEvent(keyEvent: KeyEvent, doGlobalAction: (action: Int) -> Unit): Boolean {
        return this.appScreenAccessor.onKeyEvent(keyEvent, doGlobalAction)
    }
}