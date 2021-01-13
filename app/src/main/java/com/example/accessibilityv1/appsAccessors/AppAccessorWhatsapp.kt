package com.example.accessibilityv1.appsAccessors

import android.os.SystemClock
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice

class AppAccessorWhatsapp(textToVoice: TextToVoice): AppAccessor(textToVoice) {
    override val appIconLabel: String get() = "whatsapp"
    override val packageName: String get() = "com.whatsapp"

    private var openingChat = false
    private lateinit var chatName: String
    private val newChatId = "com.whatsapp:id/fab"
    private val tabsId = "com.whatsapp:id/home_tab_layout"
    private val contactRowId = "com.whatsapp:id/contact_row_container"
    private val contactNameId = "com.whatsapp:id/conversations_row_contact_name"
    private val classNameConversation = "com.whatsapp.Conversation"

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        this.openChat(event)
        this.onChatOpened(event)
        this.speakOnFocus(event)
        this.speakChatInfo(event)
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

    private fun onChatOpened(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType &&
            event.className.toString() == this.classNameConversation) {
            this.openingChat = false
        }
    }

    private fun speakChatInfo(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_SELECTED == event.eventType &&
            event.contentDescription != null) {
            val className = event.className.toString()
            val contentDescription = event.contentDescription.toString()
            if (className == this.classNameImageView) {
                this.chatName = contentDescription
            }
            if (className == this.classNameTextView && contentDescription.contains("no leídos")) {
                this.speak(this.chatName + " " + event.contentDescription.toString())
            } else {
                this.speak(this.chatName)
            }
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