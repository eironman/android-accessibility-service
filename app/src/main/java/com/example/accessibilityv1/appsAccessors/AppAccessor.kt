package com.example.accessibilityv1.appsAccessors

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.KeyAction
import com.example.accessibilityv1.UserInput

abstract class AppAccessor constructor(var textToVoice: TextToVoice) {
    abstract val packageName: String
    abstract val appIconLabel: String

    protected val classNameTextView = "android.widget.TextView"
    protected val classNameImageView = "android.widget.ImageView"
    protected val classNameRelativeLayout = "android.widget.RelativeLayout"

    protected val keyEventDefaultResponse = false
    protected val keyEventStopPropagationResponse = true
    private var userInput: UserInput = UserInput()

    abstract fun onAccessibilityEvent(event: AccessibilityEvent)

    fun onKeyEvent(keyEvent: KeyEvent, doGlobalAction: (action: Int) -> Unit): Boolean {
        return when (userInput.onKeyEvent(keyEvent)) {
            KeyAction.BUTTON_HOME_PRESSED -> this.onButtonHomePressed()
            KeyAction.BUTTON_HOME_RELEASED -> this.onButtonHomeReleased(doGlobalAction)
            KeyAction.BUTTON_A_PRESSED -> this.onButtonAPressed()
            KeyAction.BUTTON_A_RELEASED -> this.onButtonAReleased()
            KeyAction.BUTTON_B_PRESSED -> this.onButtonBPressed()
            KeyAction.BUTTON_B_RELEASED -> this.onButtonBReleased(doGlobalAction)
            KeyAction.BUTTON_UP_PRESSED -> this.onButtonUpPressed()
            KeyAction.BUTTON_UP_RELEASED -> this.onButtonUpReleased()
            KeyAction.BUTTON_DOWN_PRESSED -> this.onButtonDownPressed()
            KeyAction.BUTTON_DOWN_RELEASED -> this.onButtonDownReleased()
            KeyAction.BUTTON_LEFT_PRESSED -> this.onButtonLeftPressed()
            KeyAction.BUTTON_LEFT_RELEASED -> this.onButtonLeftReleased()
            KeyAction.BUTTON_RIGHT_PRESSED -> this.onButtonRightPressed()
            KeyAction.BUTTON_RIGHT_RELEASED -> this.onButtonRightReleased()
            else -> true
        }
    }

    protected fun speakOnFocus(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_FOCUSED == event.eventType) {
            if (event.contentDescription != null) {
                this.speak(event.contentDescription.toString())
            } else {
                this.speak(event.text.toString())
            }
        }
    }

    protected fun speak(text: String) {
        textToVoice.speak(this.sanitizeText(text))
    }


    private fun sanitizeText(text: String): String {
        return text
                .replace("[", "")
                .replace("]", "")
    }

    protected open fun onButtonHomePressed(): Boolean {
        Log.i("AA AppAccessor", "BUTTON HOME PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonHomeReleased(doGlobalAction: (action: Int) -> Unit): Boolean {
        doGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
        Log.i("AA  AppAccessor", "BUTTON HOME RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonAPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON A PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonAReleased(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON A RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonBPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON B PRESSED")
        return this.keyEventStopPropagationResponse
    }

    protected open fun onButtonBReleased(doGlobalAction: (action: Int) -> Unit): Boolean {
        this.speak("Atrás")
        doGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        Log.i("AA  AppAccessor", "BUTTON B RELEASED")
        return this.keyEventStopPropagationResponse
    }

    protected open fun onButtonUpPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON UP PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonUpReleased(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON UP RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonDownPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON DOWN PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonDownReleased(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON DOWN RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonLeftPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON LEFT PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonLeftReleased(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON LEFT RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonRightPressed(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON RIGHT PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonRightReleased(): Boolean {
        Log.i("AA  AppAccessor", "BUTTON RIGHT RELEASED")
        return this.keyEventDefaultResponse
    }
}