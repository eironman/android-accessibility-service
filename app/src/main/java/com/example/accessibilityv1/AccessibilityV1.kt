package com.example.accessibilityv1

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.accessibilityv1.appsAccessors.AppAccessor
import com.example.accessibilityv1.appsAccessors.AppAccessorFactory

class AccessibilityV1: AccessibilityService() {

    // diego.aaron.emulador@gmail.com
    // juk47SD4tg

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
//            source.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            if (source.viewIdResourceName.isNullOrEmpty()) {
                Log.i("AA Node", source.toString())
            } else {
                Log.i("AA Node ID", source.viewIdResourceName)
            }
        }
    }

    override fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        Log.i("AA", keyEvent.toString())
        return this.appAccessor.onKeyEvent(keyEvent, ::doGlobalAction)
    }

    private fun doGlobalAction(action: Int) {
        performGlobalAction(action)
    }

    override fun onDestroy() {
        textToVoice.destroy()
        super.onDestroy()
    }

    override fun onInterrupt() {}
}