package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChats(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    override val appIconLabel: String get() = "whatsapp"
    override val packageName: String get() = "com.whatsapp"

    private var chatName = ""
    private var openingChat = false

    init {
        this.speak("Pulsa hacia abajo para navegar por los chats")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.speakOnFocus(event)
        this.speakChatName(event)
        this.openChat(event)
    }

    private fun speakChatName(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_SELECTED == event.eventType &&
            event.contentDescription != null) {
            val className = event.className.toString()
            val contentDescription = event.contentDescription.toString()
            if (className == this.classNameImageView) {
                this.chatName = contentDescription
            }
            if (className == this.classNameTextView && contentDescription.contains("no leído")) {
                this.speak(this.chatName + " " + event.contentDescription.toString())
            } else {
                this.speak(this.chatName)
            }
        }
    }

    private fun openChat(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.eventType &&
            event.className.toString() == this.classNameRelativeLayout &&
            event.source != null &&
            !this.openingChat) {
                event.source.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                this.openingChat = true
        }
    }

    override fun onButtonRightPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonLeftPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonRightReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonLeftReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }
}