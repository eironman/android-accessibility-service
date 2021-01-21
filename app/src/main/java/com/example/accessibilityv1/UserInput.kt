package com.example.accessibilityv1

import android.view.KeyEvent

class UserInput {
    fun onKeyEvent(keyEvent: KeyEvent): KeyAction {
        if (KeyEvent.ACTION_DOWN == keyEvent.action) {
            if (KeyEvent.KEYCODE_DPAD_UP == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_NEXT == keyEvent.keyCode) {
                return KeyAction.BUTTON_UP_PRESSED
            }
            if (KeyEvent.KEYCODE_DPAD_DOWN == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyEvent.keyCode) {
                return KeyAction.BUTTON_DOWN_PRESSED
            }
            if (KeyEvent.KEYCODE_DPAD_LEFT == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_FAST_FORWARD == keyEvent.keyCode) {
                return KeyAction.BUTTON_LEFT_PRESSED
            }
            if (KeyEvent.KEYCODE_DPAD_RIGHT == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_REWIND == keyEvent.keyCode) {
                return KeyAction.BUTTON_RIGHT_PRESSED
            }
            if (KeyEvent.KEYCODE_BUTTON_A == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_ENTER == keyEvent.keyCode) {
                return KeyAction.BUTTON_A_PRESSED
            }
            if (KeyEvent.KEYCODE_BUTTON_B == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_BACK == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_DEL == keyEvent.keyCode) {
                return KeyAction.BUTTON_B_PRESSED
            }
            if (KeyEvent.KEYCODE_HOME == keyEvent.keyCode) {
                return KeyAction.BUTTON_HOME_PRESSED
            }
        }
        if (KeyEvent.ACTION_UP == keyEvent.action) {
            if (KeyEvent.KEYCODE_DPAD_UP == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_NEXT == keyEvent.keyCode) {
                return KeyAction.BUTTON_UP_RELEASED
            }
            if (KeyEvent.KEYCODE_DPAD_DOWN == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyEvent.keyCode) {
                return KeyAction.BUTTON_DOWN_RELEASED
            }
            if (KeyEvent.KEYCODE_DPAD_LEFT == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_FAST_FORWARD == keyEvent.keyCode) {
                return KeyAction.BUTTON_LEFT_RELEASED
            }
            if (KeyEvent.KEYCODE_DPAD_RIGHT == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_MEDIA_REWIND == keyEvent.keyCode) {
                return KeyAction.BUTTON_RIGHT_RELEASED
            }
            if (KeyEvent.KEYCODE_BUTTON_A == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_ENTER == keyEvent.keyCode) {
                return KeyAction.BUTTON_A_RELEASED
            }
            if (KeyEvent.KEYCODE_BUTTON_B == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_BACK == keyEvent.keyCode ||
                    KeyEvent.KEYCODE_DEL == keyEvent.keyCode) {
                return KeyAction.BUTTON_B_RELEASED
            }
            if (KeyEvent.KEYCODE_HOME == keyEvent.keyCode) {
                return KeyAction.BUTTON_HOME_RELEASED
            }
        }
        return KeyAction.EMPTY
    }
}