package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChats(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var chatName = ""
    private var openingChat = false
    private var focusIsOnChatsTab = false
    private val contactViewIdResName = "com.whatsapp:id/contact_name"
    private val contactContainerViewIdResName = "com.whatsapp:id/contact_row_container"

    private var chatsTabNode: AccessibilityNodeInfo? = null
    private var firstChatNode: AccessibilityNodeInfo? = null

    init {
        this.speak("Usa abajo y arriba para navegar por los chats")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
//        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
//            this.traverseNodes(event.source)
//        }
        this.speakChatName(event)
        this.speakOnFocus(event)
        this.openChat(event)
    }
//
//    private fun traverseNodes(node: AccessibilityNodeInfo) {
//        for (i in node.childCount downTo 1) {
//            val child: AccessibilityNodeInfo = node.getChild(i - 1)
//            Log.i("CHILD", child.toString())
//            Log.i("CHILD", "-----------------")
//            if (this.isChatsTab(child)) {
//                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
//                this.focusIsOnChatsTab = true
//                this.chatsTabNode = node
//            } else if (this.isChat(child)) {
//                this.firstChatNode = child
//            } else {
//                traverseNodes(child)
//            }
//        }
//    }
//
//    private fun isChatsTab(node: AccessibilityNodeInfo): Boolean {
//        return node.text != null && node.text.toString() == "CHATS"
//    }
//
//    private fun isChat(node: AccessibilityNodeInfo): Boolean {
//        return node.viewIdResourceName != null &&
//                node.className.toString() == this.classNameRelativeLayout &&
//                node.viewIdResourceName == this.contactContainerViewIdResName
//    }

    private fun speakChatName(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_SELECTED == event.eventType &&
            event.contentDescription != null) {
            val className = event.className.toString()
            val contentDescription = event.contentDescription.toString()
            if (className == this.classNameImageView) {
                this.chatName = contentDescription
            }
            if (className == this.classNameTextView && contentDescription.contains("no leído")) {
                this.chatName += ", " + event.contentDescription.toString()
            }
            this.speak(this.chatName)
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
//
//    override fun onButtonDownPressed(): Boolean {
//        if (this.focusIsOnChatsTab) {
//            return this.keyEventStopPropagationResponse
//        }
//        return super.onButtonDownPressed()
//    }
//
//    override fun onButtonDownReleased(): Boolean {
//        if (this.focusIsOnChatsTab) {
//            this.focusOnFirstChat()
//            return this.keyEventStopPropagationResponse
//        }
//        return super.onButtonDownReleased()
//    }
//
//    private fun focusOnFirstChat() {
//        this.focusIsOnChatsTab = false
//        this.chatsTabNode?.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS)
//        this.firstChatNode?.performAction(AccessibilityNodeInfo.ACTION_SELECT)
//        for (i in this.firstChatNode?.childCount?.downTo(1)!!) {
//            this.firstChatNode?.getChild(i - 1)?.performAction(AccessibilityNodeInfo.ACTION_SELECT)
//        }
//    }

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