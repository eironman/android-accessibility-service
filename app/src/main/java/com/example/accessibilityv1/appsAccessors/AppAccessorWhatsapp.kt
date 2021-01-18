package com.example.accessibilityv1.appsAccessors

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp.AppScreenAccessorWhatsappChat
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp.AppScreenAccessorWhatsappChats

open class AppAccessorWhatsapp(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val appIconLabel: String get() = "whatsapp"
    override val packageName: String get() = "com.whatsapp"
    override var appScreenAccessor: AppScreenAccessor = AppScreenAccessorWhatsappChats(textToVoice)

    // CLASS NAMES
    private val classNameChats = "com.whatsapp.HomeActivity"
    private val classNameConversation = "com.whatsapp.Conversation"

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            this.appScreenAccessor = when (event.className.toString()) {
                this.classNameChats -> AppScreenAccessorWhatsappChats(textToVoice)
                this.classNameConversation -> AppScreenAccessorWhatsappChat(textToVoice)
                else -> AppScreenAccessorWhatsappChats(textToVoice)
            }
        }
        this.appScreenAccessor.onAccessibilityEvent(event)
    }
}