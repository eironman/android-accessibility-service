package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor
import java.util.*

class AppScreenAccessorWhatsappChats(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var chatName = ""
    private var openingChat = false
    private var focusChatPointer = -1
    private var chatsNodes: MutableList<AccessibilityNodeInfo> = mutableListOf()
    private val contactContainerViewIdResName = "com.whatsapp:id/contact_row_container"

    init {
        this.speak("Lista de chats")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType &&
                event.source != null) {
            this.collectChatsNodes(event.source)
            this.chatsNodes.reverse()
        }
        this.speakChatName(event)
    }

    private fun collectChatsNodes(node: AccessibilityNodeInfo) {
        if (node.viewIdResourceName != null &&
            node.className.toString() == this.classNameRelativeLayout &&
            node.viewIdResourceName == this.contactContainerViewIdResName)
        {
            this.chatsNodes.add(node)
        }
        Log.i("NODES", node.toString())
        for (i in node.childCount downTo 1) {
            if (node.getChild(i - 1) != null) {
                collectChatsNodes(node.getChild(i - 1))
            }
        }
    }

    private fun speakChatName(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_SELECTED == event.eventType &&
            event.contentDescription != null)
        {
            val className = event.className.toString()
            val contentDescription = event.contentDescription.toString()
            if (className == this.classNameImageView) {
                this.chatName = contentDescription.toLowerCase(Locale.ROOT)
            }
            if (className == this.classNameTextView && contentDescription.contains("no leído")) {
                this.chatName += ", " + event.contentDescription.toString()
            }
            this.speak(this.chatName)
        }
    }

    override fun onButtonAReleased(doGesture: (gesture: GestureDescription) -> Unit?): Boolean {
        this.openChat()
        return this.keyEventStopPropagationResponse
    }

    private fun openChat() {
        if (this.focusChatPointer > -1 && !this.openingChat) {
            this.openingChat = true
            this.chatsNodes[this.focusChatPointer].performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onButtonUpReleased(): Boolean {
        this.traverseUpChats()
        return this.keyEventStopPropagationResponse
    }

    private fun traverseUpChats() {
        if (this.focusChatPointer > -1) {
            this.chatsNodes[this.focusChatPointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLEAR_SELECTION)
        }
        if (this.focusChatPointer > 0) {
            this.focusChatPointer--
        }
        if (this.focusChatPointer > -1) {
            this.chatsNodes[this.focusChatPointer]
                .performAction(AccessibilityNodeInfo.ACTION_SELECT)
        }
    }

    override fun onButtonDownReleased(): Boolean {
        this.traverseDownChats()
        return this.keyEventStopPropagationResponse
    }

    private fun traverseDownChats() {
        if (this.focusChatPointer > -1) {
            this.chatsNodes[this.focusChatPointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLEAR_SELECTION)
        }
        if (this.focusChatPointer < this.chatsNodes.size - 1) {
            this.focusChatPointer++
        }
        this.chatsNodes[this.focusChatPointer]
            .performAction(AccessibilityNodeInfo.ACTION_SELECT)
    }

    override fun onButtonAPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonUpPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonLeftPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonLeftReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonRightPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonRightReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }
}