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

    // VIEW IDS
    protected val chatMessagesListId = "android:id/list"
    protected val messageDateId = "com.whatsapp:id/date"
    protected val newChatButtonId = "com.whatsapp:id/fab"
    protected val chatTextInputId = "com.whatsapp:id/entry"
    protected val chatMessageTextId = "com.whatsapp:id/message_text"
    protected val tabsId = "com.whatsapp:id/home_tab_layout"
    protected val cameraButtonId = "com.whatsapp:id/camera_btn"
    protected val emojiButtonId = "com.whatsapp:id/emoji_picker_btn"
    protected val contactRowId = "com.whatsapp:id/contact_row_container"
    protected val attachButtonId = "com.whatsapp:id/input_attach_button"
    protected val contactNameId = "com.whatsapp:id/conversations_row_contact_name"
    protected val voiceMessageButtonId = "com.whatsapp:id/voice_note_btn"

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