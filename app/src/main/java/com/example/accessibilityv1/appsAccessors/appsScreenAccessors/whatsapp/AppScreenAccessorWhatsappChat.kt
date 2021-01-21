package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.whatsapp

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor

class AppScreenAccessorWhatsappChat(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var recordingAudioMessage = false
    private var readMessagePointer = -1
    private val chatPrivacyInfoId = "com.whatsapp:id/info"
    private val textMessageId = "com.whatsapp:id/message_text"
    private var playAudioMessageButtonId = "com.whatsapp:id/control_btn"
    private val recordAudioMessageButtonId = "com.whatsapp:id/voice_note_btn"
    private var recordAudioMessageButtonCoordinates: Pair<Float, Float>? = null

    private var selectedPlayAudioNode: AccessibilityNodeInfo? = null
    private var recordAudioMessageButtonNode: AccessibilityNodeInfo? = null
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
        if (node.viewIdResourceName != null &&
            node.viewIdResourceName.toString() == this.recordAudioMessageButtonId)
        {
            this.recordAudioMessageButtonNode = node
            this.recordAudioMessageButtonCoordinates = this.getNodeCenterCoordinates(node)
        }
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
            if (node.viewIdResourceName == this.textMessageId) {
                this.speak(node.text.toString())
            }
            if (node.viewIdResourceName == this.chatPrivacyInfoId) {
                this.speak("No hay más mensajes")
            }
            if (node.viewIdResourceName == this.playAudioMessageButtonId) {
                this.selectedPlayAudioNode = node
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
    }

    private fun stopAutoplayAudioMessage(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED == event.eventType &&
                event.source != null) {
            val node: AccessibilityNodeInfo = event.source
            if (this.selectedPlayAudioNode != null &&
                node.contentDescription != null &&
                node.contentDescription == "Pausar" &&
                node.className == this.classNameImageButton &&
                node != this.selectedPlayAudioNode)
            {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                this.selectedPlayAudioNode = null
            }
        }
    }

    override fun onButtonUpReleased(): Boolean {
        if (!this.recordingAudioMessage) {
            if (this.readMessagePointer < this.chatMessagesNodes.size - 1) {
                this.deselectChatMessage()
                this.readMessagePointer++
                this.selectChatMessage()
            } else {
                this.speak("No hay más mensajes")
            }
        }
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonDownReleased(): Boolean {
        if (!this.recordingAudioMessage) {
            if (this.readMessagePointer >= 0) {
                this.deselectChatMessage()
                this.readMessagePointer--
                this.selectChatMessage()
            }
            if (this.readMessagePointer == -1) {
                this.speak("Fin del chat")
            }
        }
        return this.keyEventStopPropagationResponse
    }

    private fun selectChatMessage() {
        if (this.chatMessagesNodes.isNotEmpty() && this.readMessagePointer > -1) {
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

    override fun onButtonAReleased(doGesture: (gesture: GestureDescription) -> Unit?): Boolean {
        if (this.recordAudioMessageButtonNode != null) {
            if (!this.recordingAudioMessage) {
                this.startRecordingAudio(doGesture)
            } else {
                this.stopRecordingAudio(doGesture)
            }
        }
        return this.keyEventStopPropagationResponse
    }

    private fun startRecordingAudio(doGesture: (gesture: GestureDescription) -> Unit?) {
        if (this.recordAudioMessageButtonCoordinates != null) {
            doGesture(this.getClickGesture(
                this.recordAudioMessageButtonCoordinates!!, willContinue = true))
            this.recordingAudioMessage = true
        }
    }

    private fun stopRecordingAudio(doGesture: (gesture: GestureDescription) -> Unit?) {
        if (this.recordAudioMessageButtonCoordinates != null) {
            doGesture(this.getClickGesture(this.recordAudioMessageButtonCoordinates!!))
            this.recordingAudioMessage = false
        }
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

    override fun onButtonRightReleased(): Boolean {
        return this.keyEventStopPropagationResponse
    }

    override fun onButtonRightPressed(): Boolean {
        return this.keyEventStopPropagationResponse
    }
}