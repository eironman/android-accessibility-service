package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChat(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var readMessagePointer = -1
    private val chatPrivacyInfoId = "com.whatsapp:id/info"
    private var playButtonId = "com.whatsapp:id/control_btn"
    private val chatMessageTextId = "com.whatsapp:id/message_text"
    private var selectedPlayAudioNode: AccessibilityNodeInfo? = null
    private var chatMessagesNodes: MutableList<AccessibilityNodeInfo> = mutableListOf()

    init {
        this.speak("Chat abierto")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            this.collectConversationNodes(event.source)
        }
        this.readChatMessage(event)
        this.stopAutoplayAudioMessage(event)
    }

    private fun collectConversationNodes(node: AccessibilityNodeInfo) {
        if (node.className.toString() == this.classNameViewGroup) {
            this.chatMessagesNodes.add(node)
        }
        for (i in node.childCount downTo 1) {
            if (node.getChild(i - 1) != null) {
                collectConversationNodes(node.getChild(i - 1))
            }
        }
    }

    private fun readChatMessage(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_SELECTED == event.eventType) {
            val node: AccessibilityNodeInfo = event.source
            if (node.viewIdResourceName == this.chatMessageTextId) {
                this.speak(node.text.toString())
            }
            if (node.viewIdResourceName == this.chatPrivacyInfoId) {
                this.speak("No hay más mensajes")
            }
            if (node.viewIdResourceName == this.playButtonId) {
                this.selectedPlayAudioNode = node
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    private fun stopAutoplayAudioMessage(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED == event.eventType) {
            val node: AccessibilityNodeInfo = event.source
            if (this.selectedPlayAudioNode != null &&
                node.contentDescription != null &&
                node.contentDescription == "Pausar" &&
                node.className == this.classNameImageButton &&
                node != this.selectedPlayAudioNode
            ) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                this.selectedPlayAudioNode = null
            }
        }
    }

    private fun selectChatMessage() {
        if (this.chatMessagesNodes.isNotEmpty()) {
            this.chatMessagesNodes[this.readMessagePointer]
                .performAction(AccessibilityNodeInfo.ACTION_SELECT)
        }
    }

    private fun deselectChatMessage() {
        if (this.chatMessagesNodes.isNotEmpty() && this.readMessagePointer >= 0) {
            this.chatMessagesNodes[this.readMessagePointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLEAR_SELECTION)
        }
    }

    override fun onButtonAPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonAReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonUpPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonUpReleased(): Boolean {
        if (this.readMessagePointer < this.chatMessagesNodes.size - 1) {
            this.deselectChatMessage()
            this.readMessagePointer++
            this.selectChatMessage()
        } else {
            this.speak("No hay más mensajes")
        }
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownReleased(): Boolean {
        if (this.readMessagePointer >= 0) {
            this.deselectChatMessage()
            this.readMessagePointer--
            if (this.readMessagePointer > -1) {
                this.selectChatMessage()
            }
        }
        if (this.readMessagePointer == -1) {
            this.speak("Fin del chat")
        }
        return this.keyEventStopPropagationResponse
    }
}