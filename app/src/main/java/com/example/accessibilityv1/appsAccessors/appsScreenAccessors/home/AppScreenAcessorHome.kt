package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.home

import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAcessorHome(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    override val packageName: String get() = ""
    override val appIconLabel: String get() = ""

    private var homeScreenEvents = 0

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.speakOnFocus(event)
        this.speakHomeScreen(event)
        // this.speakAppOpened(event)
    }

    private fun speakHomeScreen(event: AccessibilityEvent) {
        if (isHomeScreen(event)) {
            this.homeScreenEvents++
            if (this.homeScreenEvents == 2) {
                this.homeScreenEvents = 0
                this.speak("Pantalla de inicio")
            }
        }
    }

    private fun isHomeScreen(event: AccessibilityEvent): Boolean {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType &&
                event.text.toString().contains("inicio")
    }

    private fun speakAppOpened(event: AccessibilityEvent) {
        if (isAppOpened(event)) {
            this.speak("Has abierto " + event.contentDescription.toString())
        }
    }

    private fun isAppOpened(event: AccessibilityEvent): Boolean {
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.eventType &&
            event.packageName.toString().contains("launcher")) {
            return true
        }
        return false
    }
}