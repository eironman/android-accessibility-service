package com.example.accessibilityv1

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.appsAccessors.AppAccessor
import com.example.accessibilityv1.appsAccessors.AppAccessorFactory

class AccessibilityV1: AccessibilityService() {

    private lateinit var userInput: UserInput
    private lateinit var appAccessor: AppAccessor
    private lateinit var textToVoice: TextToVoice
    private lateinit var appAccessorFactory: AppAccessorFactory

    override fun onServiceConnected() {
        super.onServiceConnected()
        this.userInput = UserInput()
        this.textToVoice = TextToVoice(this)
        this.appAccessorFactory = AppAccessorFactory(this.textToVoice)
        this.appAccessor = this.appAccessorFactory.getAccessor(AccessibilityEvent.obtain())
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        Log.i("AA", accessibilityEvent.toString())
        this.printNode(accessibilityEvent)
        this.appAccessor = this.appAccessorFactory.getAccessor(accessibilityEvent)
        this.appAccessor.onAccessibilityEvent(accessibilityEvent)
    }

    private fun printNode(event: AccessibilityEvent) {
        if (event.source != null) {
            val source: AccessibilityNodeInfo = event.source
            if (source.viewIdResourceName.isNullOrEmpty()) {
                Log.i("AA Node", source.toString())
            } else {
                Log.i("AA Node ID", source.viewIdResourceName)
            }
        }
    }

    override fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        Log.i("KEY EVENT", keyEvent.toString())
        return this.appAccessor.onKeyEvent(keyEvent, ::doGlobalAction, ::doGesture)
    }

    private fun doGlobalAction(action: Int) {
        Log.i("GLOBAL ACTION", action.toString())
        performGlobalAction(action)
    }

    private fun doGesture(gesture: GestureDescription) {
        this.dispatchGesture(gesture, null, null)
    }

    override fun onDestroy() {
        textToVoice.destroy()
        super.onDestroy()
    }

    override fun onInterrupt() {}
}