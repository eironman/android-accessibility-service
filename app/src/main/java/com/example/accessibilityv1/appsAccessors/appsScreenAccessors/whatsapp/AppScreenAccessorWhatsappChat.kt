package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.AppAccessorWhatsapp
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChat(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    override val appIconLabel: String get() = "whatsapp"
    override val packageName: String get() = "com.whatsapp"

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
//        if (!node.viewIdResourceName.isNullOrEmpty()) {
//            when(node.viewIdResourceName.toString()) {
//                this.chatMessagesListId -> this.chatMessagesParentNode = node
//                this.chatMessageTextId -> this.chatMessagesNodes.add(node)
//                else -> {}
//            }
//        }
        for (i in node.childCount downTo 1) {
            if (node.getChild(i - 1) != null) {
                Log.i("CHILDREN", node.getChild(i - 1).toString())
                Log.i("CHILDREN CLASSNAME", node.getChild(i - 1).className.toString())
                collectConversationNodes(node.getChild(i - 1))
            }
        }
    }

    private fun readChatMessage() {
        if (this.chatMessagesNodes.isNotEmpty()) {
            Log.i("FOCUS ON", this.chatMessagesNodes[this.readMessagePointer].toString())
            this.chatMessagesNodes[this.readMessagePointer].performAction(AccessibilityNodeInfo.ACTION_SELECT)
//            this.speak(this.chatMessagesNodes[this.readMessagePointer].text.toString())
        }
    }

    override fun onButtonUpPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonUpReleased(): Boolean {
        if (this.readMessagePointer < this.chatMessagesNodes.size) {
            this.readMessagePointer++
        }
        this.readChatMessage()
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownReleased(): Boolean {
        if (this.readMessagePointer > 0) {
            this.readMessagePointer--
        }
        this.readChatMessage()
        return this.keyEventStopPropagationResponse
    }
}