package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChat(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var readMessagePointer = -1
    private lateinit var chatMessagesParentNode: AccessibilityNodeInfo
    private var chatMessagesNodes: MutableList<AccessibilityNodeInfo> = mutableListOf()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            this.collectConversationNodes(event.source)
        }
    }

    private fun collectConversationNodes(node: AccessibilityNodeInfo) {
        if (node.className.toString() == this.classNameViewGroup) {
            this.chatMessagesNodes.add(node)
        }
        for (i in node.childCount downTo 1) {
            if (node.getChild(i - 1) != null) {
                Log.i("CHILDREN", node.getChild(i - 1).toString())
                Log.i("CHILDREN CLASSNAME", node.getChild(i - 1).className.toString())
                collectConversationNodes(node.getChild(i - 1))
            }
        }
    }

    override fun onButtonUpPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonUpReleased(): Boolean {
        if (this.readMessagePointer < this.chatMessagesNodes.size - 1) {
            this.deselectChatMessage()
            this.readMessagePointer++
            this.selectChatMessage()
        }
        return this.keyEventStopPropagationResponse
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

    override fun onButtonDownPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownReleased(): Boolean {
        if (this.readMessagePointer > 0) {
            this.deselectChatMessage()
            this.readMessagePointer--
            this.selectChatMessage()
        }
        return this.keyEventStopPropagationResponse
    }
}