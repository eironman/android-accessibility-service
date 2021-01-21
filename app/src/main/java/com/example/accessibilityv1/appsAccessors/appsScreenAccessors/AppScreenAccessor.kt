package com.example.accessibilityv1.appsAccessors.appsScreenAccessors

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.KeyAction
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.UserInput

abstract class AppScreenAccessor constructor(var textToVoice: TextToVoice) {
    protected val classNameTextView = "android.widget.TextView"
    protected val classNameViewGroup = "android.view.ViewGroup"
    protected val classNameImageView = "android.widget.ImageView"
    protected val classNameImageButton = "android.widget.ImageButton"
    protected val classNameRelativeLayout = "android.widget.RelativeLayout"

    private val keyEventDefaultResponse = false
    private var userInput: UserInput = UserInput()
    protected val keyEventStopPropagationResponse = true

    abstract fun onAccessibilityEvent(event: AccessibilityEvent)

    protected fun getClickGesture(coordinates: Pair<Float, Float>,
                                  duration: Long = 1,
                                  willContinue: Boolean = false): GestureDescription {
        val clickPath = Path()
        clickPath.moveTo(coordinates.first, coordinates.second)
        return GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(clickPath, 0, duration, willContinue))
            .build()
    }

    protected fun getNodeCenterCoordinates(node: AccessibilityNodeInfo): Pair<Float, Float>? {
        val regexMatch =
            Regex("boundsInScreen: Rect\\(([0-9]+), ([0-9]+) - ([0-9]+), ([0-9]+)\\)")
                .find(node.toString())
        if (regexMatch != null) {
            val (left, top, right, bottom) = regexMatch.destructured
            return Pair(
                (left.toFloat() + right.toFloat()) / 2,
                (top.toFloat() + bottom.toFloat()) / 2)
        }

        return null
    }

    protected fun speak(text: String) {
        textToVoice.speak(this.sanitizeText(text))
    }

    private fun sanitizeText(text: String): String {
        return text
            .replace("[", "")
            .replace("]", "")
    }

    protected fun speakOnFocus(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_VIEW_FOCUSED == event.eventType ||
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED == event.eventType) {
            if (event.contentDescription != null) {
                this.speak(event.contentDescription.toString())
            } else {
                this.speak(event.text.toString())
            }
        }
    }

    // KEY ACTIONS

    fun onKeyEvent(keyEvent: KeyEvent,
                   doGlobalAction: (action: Int) -> Unit,
                   doGesture: (gesture: GestureDescription) -> Unit): Boolean {
        return when (userInput.onKeyEvent(keyEvent)) {
            KeyAction.BUTTON_HOME_PRESSED -> this.onButtonHomePressed()
            KeyAction.BUTTON_HOME_RELEASED -> this.onButtonHomeReleased(doGlobalAction)
            KeyAction.BUTTON_A_PRESSED -> this.onButtonAPressed()
            KeyAction.BUTTON_A_RELEASED -> this.onButtonAReleased(doGesture)
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
            else -> this.keyEventDefaultResponse
        }
    }

    protected open fun onButtonHomePressed(): Boolean {
        Log.i("AA AppScreenAccessor", "BUTTON HOME PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonHomeReleased(doGlobalAction: (action: Int) -> Unit): Boolean {
        // https://stackoverflow.com/questions/29200046/accessibility-service-performglobalaction-returns-false
        Thread.sleep(1000)
        doGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
        Log.i("APP SCREEN ACCESSOR", "BUTTON HOME RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonAPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON A PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonAReleased(doGesture: (gesture: GestureDescription) -> Unit?): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON A RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonBPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON B PRESSED")
        return this.keyEventStopPropagationResponse
    }

    protected open fun onButtonBReleased(doGlobalAction: (action: Int) -> Unit): Boolean {
        doGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
        Log.i("APP SCREEN ACCESSOR", "BUTTON B RELEASED")
        return this.keyEventStopPropagationResponse
    }

    protected open fun onButtonUpPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON UP PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonUpReleased(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON UP RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonDownPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON DOWN PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonDownReleased(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON DOWN RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonLeftPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON LEFT PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonLeftReleased(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON LEFT RELEASED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonRightPressed(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON RIGHT PRESSED")
        return this.keyEventDefaultResponse
    }

    protected open fun onButtonRightReleased(): Boolean {
        Log.i("APP SCREEN ACCESSOR", "BUTTON RIGHT RELEASED")
        return this.keyEventDefaultResponse
    }
}