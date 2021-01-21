package com.example.accessibilityv1.appsAccessors.appsScreenAccessors.home

import android.accessibilityservice.GestureDescription
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.TextToVoice
import com.example.accessibilityv1.appsAccessors.appsScreenAccessors.AppScreenAccessor
import java.util.*

class AppScreenAcessorHome(textToVoice: TextToVoice): AppScreenAccessor(textToVoice) {
    private var homeScreenEvents = 0
    private var focusAppLauncherPointer = -1
    private var appLauncherNodes: MutableList<AccessibilityNodeInfo> = mutableListOf()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType) {
            this.collectAppLauncherNodes(event.source)
            Log.i("CHILDREN", this.appLauncherNodes.toString())
        }
        this.speakOnFocus(event)
        this.speakHomeScreen(event)
    }

    private fun speakHomeScreen(event: AccessibilityEvent) {
        if (isHomeScreen(event)) {
            this.homeScreenEvents++
            if (this.homeScreenEvents == 2) {
                this.homeScreenEvents = 0
                this.speak("Pantalla de inicio")
            }
        }
    }

    private fun isHomeScreen(event: AccessibilityEvent): Boolean {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.eventType &&
                event.text.toString().toLowerCase(Locale.ROOT).contains("inicio")
    }

    private fun collectAppLauncherNodes(node: AccessibilityNodeInfo) {
        if (node.packageName.contains("launcher") &&
                node.className != null &&
                node.contentDescription != null &&
                node.viewIdResourceName == null &&
                !this.appLauncherNodes.contains(node) &&
                (node.className == this.classNameTextView ||
                node.className == this.classNameFrameLayout)) {
            this.appLauncherNodes.add(node)
        }
        for (i in node.childCount downTo 1) {
            if (node.getChild(i - 1) != null) {
                Log.i("CHILD", node.getChild(i - 1).toString())
                this.collectAppLauncherNodes(node.getChild(i - 1))
            }
        }
    }

    private fun speakAppOpened(event: AccessibilityEvent) {
        if (isAppOpened(event)) {
            this.speak("Has abierto " + event.contentDescription.toString())
        }
    }

    private fun isAppOpened(event: AccessibilityEvent): Boolean {
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.eventType &&
            event.packageName.toString().contains("launcher")) {
            return true
        }
        return false
    }

    override fun onButtonAReleased(doGesture: (gesture: GestureDescription) -> Unit?): Boolean {
        this.openApp()
        return this.keyEventStopPropagationResponse
    }

    private fun openApp() {
        if (this.focusAppLauncherPointer > -1) {
            this.appLauncherNodes[this.focusAppLauncherPointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onButtonUpReleased(): Boolean {
        this.traverseUpChats()
        return this.keyEventStopPropagationResponse
    }

    private fun traverseUpChats() {
        if (this.focusAppLauncherPointer > -1) {
            this.appLauncherNodes[this.focusAppLauncherPointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS)
        }
        if (this.focusAppLauncherPointer > 0) {
            this.focusAppLauncherPointer--
        }
        if (this.focusAppLauncherPointer > -1) {
            this.appLauncherNodes[this.focusAppLauncherPointer]
                .performAction(AccessibilityNodeInfo.ACTION_FOCUS)
        }
    }

    override fun onButtonDownReleased(): Boolean {
        this.traverseDownChats()
        return this.keyEventStopPropagationResponse
    }

    private fun traverseDownChats() {
        if (this.focusAppLauncherPointer > -1) {
            this.appLauncherNodes[this.focusAppLauncherPointer]
                .performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS)
        }
        if (this.focusAppLauncherPointer < this.appLauncherNodes.size - 1) {
            this.focusAppLauncherPointer++
        }
        this.appLauncherNodes[this.focusAppLauncherPointer]
            .performAction(AccessibilityNodeInfo.ACTION_FOCUS)
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